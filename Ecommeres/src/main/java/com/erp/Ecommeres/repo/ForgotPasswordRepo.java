package com.erp.Ecommeres.repo;



import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.erp.Ecommeres.entity.ForgotPassword;

@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Long> {

    @Query("SELECT f FROM ForgotPassword f WHERE f.otp = :otp AND f.user.email = :email")
    Optional<ForgotPassword> findOtpByEmail(@Param("otp") Integer otp,
                                           @Param("email") String email);

    @Query("SELECT f FROM ForgotPassword f WHERE f.user.email = :email")
    Optional<ForgotPassword> findByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM ForgotPassword f WHERE f.user.email = :email")
    void deleteByUserEmail(@Param("email") String email);
}