package com.erp.Ecommeres.homepage.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.Ecommeres.homepage.entity.CustomerFeedback;

public interface CustomerFeedbackRepo
        extends JpaRepository<CustomerFeedback, Long> {

    List<CustomerFeedback> findByProductId(Long productId);
}
