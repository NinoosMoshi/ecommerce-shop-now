package com.ninos.service.order;

import com.ninos.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    List<Order> getUserOrders(Long userId);

}
