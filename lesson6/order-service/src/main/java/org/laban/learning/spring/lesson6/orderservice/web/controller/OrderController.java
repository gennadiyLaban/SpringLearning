package org.laban.learning.spring.lesson6.orderservice.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson6.orderservice.service.OrderService;
import org.laban.learning.spring.lesson6.orderservice.web.dto.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid Order order) {
        orderService.createOrder(order);
        return ResponseEntity.created(null).build();
    }

}
