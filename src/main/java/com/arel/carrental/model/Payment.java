package com.arel.carrental.model;

import java.time.LocalDateTime;

/**
 * Represents a payment made for a rental transaction.
 */
public class Payment {

    public enum Method { CASH, CARD }

    private final String paymentId;
    private final String rentalId;
    private final double amount;
    private final Method method;
    private final LocalDateTime paidAt;

    public Payment(String paymentId, String rentalId, double amount, Method method) {
        if (paymentId == null || paymentId.isBlank()) throw new IllegalArgumentException("paymentId cannot be blank");
        if (rentalId == null || rentalId.isBlank()) throw new IllegalArgumentException("rentalId cannot be blank");
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
        if (method == null) throw new IllegalArgumentException("method cannot be null");

        this.paymentId = paymentId;
        this.rentalId = rentalId;
        this.amount = amount;
        this.method = method;
        this.paidAt = LocalDateTime.now();
    }

    public String getPaymentId() { return paymentId; }
    public String getRentalId() { return rentalId; }
    public double getAmount() { return amount; }
    public Method getMethod() { return method; }
    public LocalDateTime getPaidAt() { return paidAt; }
}
