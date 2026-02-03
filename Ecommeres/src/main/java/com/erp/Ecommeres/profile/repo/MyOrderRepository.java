package com.erp.Ecommeres.profile.repo;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.Ecommeres.homepage.entity.Order;

public interface MyOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Order> findById(Long id);
}
