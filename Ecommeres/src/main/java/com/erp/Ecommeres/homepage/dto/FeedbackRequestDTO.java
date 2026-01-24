package com.erp.Ecommeres.homepage.dto;

import jakarta.validation.constraints.*;

public class FeedbackRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotBlank
    private String category;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;

    // ===== GETTERS & SETTERS =====

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
