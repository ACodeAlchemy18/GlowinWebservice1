package com.erp.Ecommeres.profile.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.admindashboard.entity.Product;
import com.erp.Ecommeres.homepage.entity.Order;
import com.erp.Ecommeres.profile.dto.OrderDTO;
import com.erp.Ecommeres.profile.repo.MyOrderRepository;
import com.erp.Ecommeres.admindashboard.repo.ProductRepo;


@Service
public class MyOrderService {

    private final MyOrderRepository orderRepo;
    private final ProductRepo productRepo;

    public MyOrderService(MyOrderRepository orderRepo,
                          ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    // 🔥 FETCH ORDERS + PRODUCT IMAGE
    public List<OrderDTO> getMyOrders(Long userId) {

        List<Order> orders =
                orderRepo.findByUserIdOrderByCreatedAtDesc(userId);

        return orders.stream().map(o -> {

            OrderDTO dto = new OrderDTO();

            // ===== ORDER FIELDS =====
            dto.setId(o.getId());
            dto.setProductId(o.getProductId()); 
            dto.setProductName(o.getProductName());
            dto.setQuantity(o.getQuantity());
            dto.setTotalPrice(o.getTotalPrice());
            dto.setStatus(o.getStatus());
            dto.setStatus(o.getStatus());
            dto.setAddress(o.getAddress());
            dto.setPaymentOption(o.getPaymentOption());


            dto.setShippingPartner(o.getShippingPartner());
            dto.setPartnerContact(o.getPartnerContact());
            dto.setExpectedDelivery(o.getExpectedDelivery());

            // ===== PRODUCT IMAGE =====
            if (o.getProductId() != null) {
                productRepo.findById(o.getProductId())
                        .ifPresent(p ->
                                dto.setImageUrl(p.getImageUrl())
                        );
            }

            return dto;

        }).toList();
    }

    // 🔥 FETCH SINGLE ORDER (WITH IMAGE)
    public OrderDTO getOrderById(Long orderId) {

        Order o = orderRepo.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        OrderDTO dto = new OrderDTO();

        dto.setId(o.getId());
        dto.setProductName(o.getProductName());
        dto.setProductId(o.getProductId()); 
        dto.setQuantity(o.getQuantity());
        dto.setTotalPrice(o.getTotalPrice());
        dto.setStatus(o.getStatus());
        dto.setPaymentOption(o.getPaymentOption());

        dto.setAddress(o.getAddress());

        dto.setShippingPartner(o.getShippingPartner());
        dto.setPartnerContact(o.getPartnerContact());
        dto.setExpectedDelivery(o.getExpectedDelivery());

        if (o.getProductId() != null) {
            productRepo.findById(o.getProductId())
                    .ifPresent(p ->
                            dto.setImageUrl(p.getImageUrl())
                    );
        }

        return dto;
    }
}
