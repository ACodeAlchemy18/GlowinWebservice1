package com.erp.Ecommeres.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.erp.Ecommeres.entity.ForgotPassword;
import java.util.Optional;

@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Long> {

    Optional<ForgotPassword> findByUserEmail(String email);

    @Transactional
    void deleteByUserEmail(String email);
}
