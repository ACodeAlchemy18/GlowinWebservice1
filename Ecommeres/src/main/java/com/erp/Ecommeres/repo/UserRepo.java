package com.erp.Ecommeres.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.erp.Ecommeres.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    List<User> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String fullName,
            String email
    );

    // ✅ FIXED UPDATE PASSWORD QUERY
    @Modifying
    @Transactional
    @Query("""
        UPDATE User u
        SET u.password = :password
        WHERE u.email = :email
    """)
    void updatePassword(@Param("email") String email,
                        @Param("password") String password);
}
