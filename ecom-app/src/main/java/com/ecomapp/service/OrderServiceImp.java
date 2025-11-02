package com.ecomapp.service;

import com.ecomapp.Repository.CartItemRepository;
import com.ecomapp.Repository.MyUserRepository;
import com.ecomapp.Repository.OrderItemRepository;
import com.ecomapp.Repository.OrderRepository;
import com.ecomapp.dto.OrderItemDto;
import com.ecomapp.entity.OrderItem;
import com.ecomapp.dto.OrderResponse;
import com.ecomapp.entity.CartItem;
import com.ecomapp.entity.MyUsers;
import com.ecomapp.entity.Order;
import com.ecomapp.entity.OrderStatus;
import com.ecomapp.exception.EmptyCartException;
import com.ecomapp.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final MyUserRepository userRepository;

    private final CartItemRepository cartItemRepository;

    private final CartItemService cartItemService;


    public OrderServiceImp(OrderRepository orderRepository, OrderItemRepository orderItemRepository, MyUserRepository userRepository, CartItemRepository cartItemRepository, CartItemService cartItemService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartItemService = cartItemService;
    }

    @Transactional
    @Override
    public OrderResponse createOrder(String userId) {

        //Validate the user
        MyUsers findUser = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("User Not Found with the Id: " + userId));

        //validate the cart Items
        List<CartItem> cartItemsByUser = cartItemService.getAllCartItemsByUser(userId);

        if(cartItemsByUser.isEmpty()) {
            throw new EmptyCartException("No cart items found for user " + userId);
        }

        //Calculate the total Price
        BigDecimal totalPrice = cartItemsByUser.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //Create Order
        Order newOrder = new Order();
        newOrder.setUser(findUser);
        newOrder.setStatus(OrderStatus.CONFIRMED);
        newOrder.setTotalPrice(totalPrice);

        List<OrderItem> orderItemList = cartItemsByUser.stream()
                .map(item -> new OrderItem(
                        null,
                         item.getProduct(),
                         item.getQuantity(),
                         item.getPrice(),
                         newOrder
                )).collect(Collectors.toList());

        newOrder.setItems(orderItemList);
        Order placedOrder = orderRepository.save(newOrder);

        //clear cart
        cartItemService.clearCartByUser(userId);

        //convert to the Order Response
        return orderToOrderResponse(placedOrder);
    }

    private OrderResponse orderToOrderResponse(Order order) {

        return new OrderResponse(
                     order.getId(),
                     order.getTotalPrice(),
                     order.getStatus().name(),
                     order.getItems().stream()
                             .map(orderItemDto -> new OrderItemDto(
                                     orderItemDto.getId(),
                                     orderItemDto.getProduct().getId(),
                                     orderItemDto.getQuantity(),
                                     orderItemDto.getPrice()
                             )).toList(),
                     order.getCreatedAt()
                );
    }


}
