package com.ninos.controller;

import com.ninos.dto.OrderDTO;
import com.ninos.model.Order;
import com.ninos.response.ApiResponse;
import com.ninos.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    //    @PostMapping("/user/order")
//    public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long userId) {
//       Order order = orderService.placeOrder(userId);
//       return ResponseEntity.ok().body(new ApiResponse("Order Placed successfully!", order));
//    }
    @PostMapping("/user/order")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long userId) {
        OrderDTO orderDTO = orderService.placeOrder(userId);
        return ResponseEntity.ok().body(new ApiResponse("Order Placed successfully!", orderDTO));
    }


    //    @GetMapping("/user/{userId}/order")
//    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
//        List<Order> orders = orderService.getUserOrders(userId);
//        return ResponseEntity.ok().body(new ApiResponse("Success!", orders));
//    }
    @GetMapping("/user/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        List<OrderDTO> orderDTOS = orderService.getUserOrders(userId);
        return ResponseEntity.ok().body(new ApiResponse("Success!", orderDTOS));
    }


}
