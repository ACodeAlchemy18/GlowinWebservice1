package com.erp.Ecommeres.controller;

import com.erp.Ecommeres.service.ForgotPasswordService;
import com.erp.Ecommeres.utils.ChangePassword;
import com.erp.Ecommeres.repo.UserRepo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/forgot-password")
public class ForgotPasswordController {

    private final ForgotPasswordService service;
    private final PasswordEncoder encoder;
    private final UserRepo userRepo;

    public ForgotPasswordController(ForgotPasswordService service,
                                    PasswordEncoder encoder,
                                    UserRepo userRepo) {
        this.service = service;
        this.encoder = encoder;
        this.userRepo = userRepo;
    }

    // ===== SEND OTP =====
    @PostMapping("/send/{email}")
    public ResponseEntity<?> sendOtp(@PathVariable String email) {
        service.sendOtp(email);
        return ResponseEntity.ok("OTP sent");
    }

    // ===== VERIFY OTP =====
    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String email,
                                       @RequestParam Integer otp) {
        service.verifyOtp(email, otp);
        return ResponseEntity.ok("OTP verified");
    }

    // ===== RESET PASSWORD =====
    @PostMapping("/reset/{email}")
    public ResponseEntity<?> resetPassword(
            @PathVariable String email,
            @RequestBody ChangePassword request) {

        if (!request.password().equals(request.repeatPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        service.resetPassword(email, request.password(), encoder, userRepo);

        return ResponseEntity.ok("Password changed successfully");
    }
}
