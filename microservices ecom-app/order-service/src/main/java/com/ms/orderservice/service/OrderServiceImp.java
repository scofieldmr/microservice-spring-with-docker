package com.ms.orderservice.service;

import com.ms.orderservice.dto.OrderItemDto;
import com.ms.orderservice.dto.OrderResponse;
import com.ms.orderservice.entity.CartItem;
import com.ms.orderservice.entity.Order;
import com.ms.orderservice.entity.OrderItem;
import com.ms.orderservice.entity.OrderStatus;
import com.ms.orderservice.exception.EmptyCartException;
import com.ms.orderservice.repository.CartItemRepository;
import com.ms.orderservice.repository.OrderItemRepository;
import com.ms.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final CartItemRepository cartItemRepository;

    private final CartItemService cartItemService;


    public OrderServiceImp(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                           CartItemRepository cartItemRepository, CartItemService cartItemService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartItemService = cartItemService;
    }

    @Transactional
    @Override
    public OrderResponse createOrder(String userId) {

        //Validate the user


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
        newOrder.setUserId(userId);
        newOrder.setStatus(OrderStatus.CONFIRMED);
        newOrder.setTotalPrice(totalPrice);

        List<OrderItem> orderItemList = cartItemsByUser.stream()
                .map(item -> new OrderItem(
                        null,
                         item.getProductId(),
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
                                     orderItemDto.getProductId(),
                                     orderItemDto.getQuantity(),
                                     orderItemDto.getPrice()
                             )).toList(),
                     order.getCreatedAt()
                );
    }


}
