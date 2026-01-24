package com.erp.Ecommeres.homepage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.homepage.dto.FeedbackRequestDTO;
import com.erp.Ecommeres.homepage.entity.CustomerFeedback;
import com.erp.Ecommeres.homepage.repo.CustomerFeedbackRepo;

@Service
public class CustomerFeedbackService {

    private final CustomerFeedbackRepo repository;

    public CustomerFeedbackService(CustomerFeedbackRepo repository) {
        this.repository = repository;
    }

    // ✅ SAVE FEEDBACK
    public CustomerFeedback saveFeedback(FeedbackRequestDTO dto) {

        CustomerFeedback feedback = new CustomerFeedback();

        feedback.setUserId(dto.getUserId());
        feedback.setProductId(dto.getProductId());
        feedback.setCategory(dto.getCategory());
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());

        return repository.save(feedback);
    }

    // ✅ GET BY PRODUCT
    public List<CustomerFeedback> getFeedbackByProduct(Long productId) {
        return repository.findByProductId(productId);
    }

    // ✅ ADMIN GET ALL
    public List<CustomerFeedback> getAllFeedback() {
        return repository.findAll();
    }
}
