package com.erp.Ecommeres.homepage.dto;

public class WishlistResponseDTO {

    private String status; // ADDED or REMOVED
    private WishlistDTO item;

    // ===== GETTERS & SETTERS =====

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WishlistDTO getItem() {
        return item;
    }

    public void setItem(WishlistDTO item) {
        this.item = item;
    }
}
