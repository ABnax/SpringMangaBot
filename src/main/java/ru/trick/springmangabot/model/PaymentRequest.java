package ru.trick.springmangabot.model;

public class PaymentRequest {
    private Long amount;
    private String userId;

    public PaymentRequest(Long amount, String userId) {
        this.amount = amount;
        this.userId = userId;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public String getUserId() {
        return userId;
    }
}
