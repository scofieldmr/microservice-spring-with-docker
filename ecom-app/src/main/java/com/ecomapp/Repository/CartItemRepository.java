package com.ecomapp.Repository;

import com.ecomapp.entity.CartItem;
import com.ecomapp.entity.MyUsers;
import com.ecomapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(MyUsers user, Product product);

    void deleteByUserAndProduct(MyUsers user, Product product);

    List<CartItem> findAllByUser(MyUsers user);

    void deleteByUser(MyUsers myUsers);
}
