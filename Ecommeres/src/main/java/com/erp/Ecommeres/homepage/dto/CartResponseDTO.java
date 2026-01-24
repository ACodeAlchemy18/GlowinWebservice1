package com.erp.Ecommeres.homepage.dto;

import java.util.List;

public class CartResponseDTO {

    private List<CartDTO> items;
    private Double subTotal;
    private Double gst;
    private Double grandTotal;

    // ===== GETTERS & SETTERS =====

    public List<CartDTO> getItems() {
        return items;
    }

    public void setItems(List<CartDTO> items) {
        this.items = items;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
