package com.erp.Ecommeres.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(
    name = "forgot_password",
    uniqueConstraints = @UniqueConstraint(columnNames = "user_id")
)
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fpid;

    @Column(nullable = false)
    private Integer otp;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;

    @Column(nullable = false)
    private boolean verified = false;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public ForgotPassword() {}

    public Long getFpid() { return fpid; }
    public Integer getOtp() { return otp; }
    public Date getExpirationTime() { return expirationTime; }
    public boolean isVerified() { return verified; }
    public User getUser() { return user; }

    public void setOtp(Integer otp) { this.otp = otp; }
    public void setExpirationTime(Date expirationTime) { this.expirationTime = expirationTime; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public void setUser(User user) { this.user = user; }
}
