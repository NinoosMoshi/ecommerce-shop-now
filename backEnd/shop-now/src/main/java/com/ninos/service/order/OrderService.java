package com.ninos.service.order;

import com.ninos.dto.OrderDTO;
import com.ninos.model.Order;

import java.util.List;

public interface OrderService {

//    Order placeOrder(Long userId);
    OrderDTO placeOrder(Long userId);

//    List<Order> getUserOrders(Long userId);
    List<OrderDTO> getUserOrders(Long userId);

}
