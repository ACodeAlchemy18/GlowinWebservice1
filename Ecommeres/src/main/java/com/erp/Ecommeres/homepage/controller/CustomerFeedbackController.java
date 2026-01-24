package com.erp.Ecommeres.homepage.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.homepage.dto.FeedbackRequestDTO;
import com.erp.Ecommeres.homepage.entity.CustomerFeedback;
import com.erp.Ecommeres.homepage.service.CustomerFeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin("*")
public class CustomerFeedbackController {

    private final CustomerFeedbackService service;

    public CustomerFeedbackController(CustomerFeedbackService service) {
        this.service = service;
    }

    // ✅ POST FEEDBACK
    @PostMapping
    public CustomerFeedback submitFeedback(
            @Valid @RequestBody FeedbackRequestDTO dto) {
        return service.saveFeedback(dto);
    }

    // ✅ GET FEEDBACK FOR PRODUCT
    @GetMapping("/product/{productId}")
    public List<CustomerFeedback> getFeedback(
            @PathVariable Long productId) {
        return service.getFeedbackByProduct(productId);
    }

    // ✅ ADMIN GET ALL FEEDBACK
    @GetMapping
    public List<CustomerFeedback> getAllFeedback() {
        return service.getAllFeedback();
    }

    // ✅ SEARCH BY PRODUCT ID
    @GetMapping("/search")
    public List<CustomerFeedback> searchByProduct(
            @RequestParam Long productId) {
        return service.getFeedbackByProduct(productId);
    }
}
