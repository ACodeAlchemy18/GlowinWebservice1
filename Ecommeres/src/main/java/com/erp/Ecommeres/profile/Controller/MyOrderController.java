package com.erp.Ecommeres.profile.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.homepage.entity.Order;
import com.erp.Ecommeres.profile.dto.OrderDTO;
import com.erp.Ecommeres.profile.service.MyOrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class MyOrderController {

    private final MyOrderService service;

    public MyOrderController(MyOrderService service) {
        this.service = service;
    }
    @GetMapping("/my/{userId}")
    public ResponseEntity<List<OrderDTO>> myOrders(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                service.getMyOrders(userId)
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderDetails(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                service.getOrderById(orderId)
        );
    }

}
