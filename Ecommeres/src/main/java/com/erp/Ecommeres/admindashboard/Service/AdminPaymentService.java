package com.erp.Ecommeres.admindashboard.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.admindashboard.dto.PaymentViewDTO;
import com.erp.Ecommeres.homepage.entity.Order;
import com.erp.Ecommeres.homepage.repo.OrderRepo;

@Service
public class AdminPaymentService {

    private final OrderRepo orderRepo;

    public AdminPaymentService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }
    
    // 🔍 SEARCH PAYMENTS
    public List<PaymentViewDTO> searchPayments(String keyword) {
        return mapToDTO(orderRepo.searchPayments(keyword));
    }
    private List<PaymentViewDTO> mapToDTO(List<Order> orders) {

        return orders.stream()
                .filter(o -> o.getPaymentOption() != null)
                .map(o -> {

                    PaymentViewDTO dto = new PaymentViewDTO();

                    dto.setPaymentId("PAY-" + o.getId());
                    dto.setOrderId(String.valueOf(o.getId()));
                    dto.setUserId(o.getUserId());
                    dto.setAmount(o.getTotalPrice());
                    dto.setMethod(o.getPaymentOption());
                    dto.setStatus(o.getStatus());
                    dto.setDate(o.getCreatedAt());

                    return dto;
                })
                .toList();
    }

    public List<PaymentViewDTO> getAllPaymentsFromOrders() {

        List<Order> orders = orderRepo.findAll();

        return orders.stream()
                .filter(o -> o.getPaymentOption() != null)
                .map(o -> {

                    PaymentViewDTO dto = new PaymentViewDTO();

                    // ✅ AUTO-GENERATED PAYMENT ID
                    dto.setPaymentId("PAY-" + o.getId());

                    dto.setOrderId(String.valueOf(o.getId()));
                    dto.setUserId(o.getUserId());
                    dto.setAmount(o.getTotalPrice());
                    dto.setMethod(o.getPaymentOption());
                    dto.setStatus(o.getStatus());
                    dto.setDate(o.getCreatedAt());

                    return dto;
                })
                .toList();
    }
}
