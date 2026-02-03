package com.erp.Ecommeres.service;



import com.erp.Ecommeres.dto.MailBody;
import com.erp.Ecommeres.entity.ForgotPassword;
import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.repo.ForgotPasswordRepo;
import com.erp.Ecommeres.repo.UserRepo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Date;

@Service
@Transactional
public class ForgotPasswordService {

    private final UserRepo userRepo;
    private final ForgotPasswordRepo forgotRepo;
    private final EmailService emailService;

    public ForgotPasswordService(UserRepo userRepo,
                                 ForgotPasswordRepo forgotRepo,
                                 EmailService emailService) {
        this.userRepo = userRepo;
        this.forgotRepo = forgotRepo;
        this.emailService = emailService;
    }

    // ===== SEND OTP =====
    public void sendOtp(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not registered"));

        forgotRepo.deleteByUserEmail(email);

        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);

        ForgotPassword fp = new ForgotPassword();
        fp.setOtp(otp);
        fp.setExpirationTime(
                new Date(System.currentTimeMillis() + 5 * 60 * 1000)
        );
        fp.setUser(user);

        forgotRepo.save(fp);

        // ✅ SEND MAIL HERE
        emailService.sendSimpleMessage(
                new MailBody(
                        email,
                        "Password Reset OTP",
                        "Your OTP is: " + otp + " (valid for 5 minutes)"
                )
        );
    }

    // ===== VERIFY OTP =====
    public void verifyOtp(String email, Integer otp) {

        ForgotPassword fp = forgotRepo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("OTP not requested"));

        if (fp.getExpirationTime().before(new Date())) {
            forgotRepo.deleteByUserEmail(email);
            throw new RuntimeException("OTP expired");
        }

        if (!fp.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        fp.setVerified(true);
        forgotRepo.save(fp);
    }

    // ===== RESET PASSWORD =====
    public void resetPassword(String email,
                              String newPassword,
                              PasswordEncoder encoder,
                              UserRepo userRepo) {

        ForgotPassword fp = forgotRepo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("OTP not verified"));

        if (!fp.isVerified()) {
            throw new RuntimeException("OTP not verified");
        }

        userRepo.updatePassword(email, encoder.encode(newPassword));

        forgotRepo.deleteByUserEmail(email);
    }
}
