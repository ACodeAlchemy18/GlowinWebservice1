package com.erp.Ecommeres.homepage.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.homepage.dto.CreateOrderRequestDTO;
import com.erp.Ecommeres.homepage.entity.Order;
import com.erp.Ecommeres.homepage.repo.OrderRepo;
import com.erp.Ecommeres.profile.entity.Address;
import com.erp.Ecommeres.profile.repo.AddressRepo;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderRepo orderRepo;
    private final AddressRepo addressRepo;

    public OrderController(OrderRepo orderRepo,
                           AddressRepo addressRepo) {
        this.orderRepo = orderRepo;
        this.addressRepo = addressRepo;
    }

    // ✅ CASH ON DELIVERY ORDER
    @PostMapping("/cod")
    public String placeCodOrder(@RequestBody CreateOrderRequestDTO dto) {

        Address address = addressRepo
                .findTopByUserIdOrderByCreatedAtDesc(dto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Address not found for userId: " + dto.getUserId()));

        String fullAddress =
                address.getAddressLine() + ", " +
                        address.getCity() + ", " +
                        address.getState() + " - " +
                        address.getPincode();

        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setProductId(dto.getProductId());
        order.setProductName(dto.getProductName());
        order.setQuantity(dto.getQuantity());
        order.setTotalPrice(dto.getTotalPrice());

        order.setPaymentOption("COD");
        order.setAddress(fullAddress);
        order.setStatus("PLACED");

        orderRepo.save(order);

        return "COD_ORDER_PLACED";
    }

    // ✅ ADMIN FETCH ALL ORDERS
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    
    @GetMapping("/search")
    public List<Order> searchOrders(@RequestParam String keyword) {
        return orderRepo.searchOrders(keyword);
    }

}
