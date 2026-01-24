package com.erp.Ecommeres.admindashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.admindashboard.Service.AdminPaymentService;
import com.erp.Ecommeres.admindashboard.dto.PaymentViewDTO;


@RestController
@RequestMapping("/api/admin/payments")
@CrossOrigin("*")
public class AdminPaymentController {

    private final com.erp.Ecommeres.admindashboard.Service.AdminPaymentService service;

    public AdminPaymentController(AdminPaymentService service) {
        this.service = service;
    }

    @GetMapping
    public List<PaymentViewDTO> getAllPayments() {
        return service.getAllPaymentsFromOrders();
    }
 // 🔍 SEARCH PAYMENTS
    @GetMapping("/search")
    public List<PaymentViewDTO> searchPayments(
            @RequestParam String keyword) {

        return service.searchPayments(keyword);
    }
}
