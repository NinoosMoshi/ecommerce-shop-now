package com.ninos.repository;

import com.ninos.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findCartByUserId(Long userId);

}
