package com.erp.Ecommeres.profile.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.Ecommeres.profile.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {

    Optional<Address> findTopByUserIdOrderByCreatedAtDesc(Long userId);

    List<Address> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Address> findFirstByUserId(Long userId);
}
