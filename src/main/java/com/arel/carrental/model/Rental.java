package com.arel.carrental.model;

import java.time.LocalDate;

/**
 * Represents a car rental transaction between a customer and the system.
 */
public class Rental {

    private final String rentalId;
    private final String carId;
    private final String customerId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final double totalFee;
    private boolean returned;

    public Rental(String rentalId, String carId, String customerId,
                  LocalDate startDate, LocalDate endDate, double totalFee) {
        if (rentalId == null || rentalId.isBlank()) throw new IllegalArgumentException("rentalId cannot be blank");
        if (carId == null || carId.isBlank()) throw new IllegalArgumentException("carId cannot be blank");
        if (customerId == null || customerId.isBlank()) throw new IllegalArgumentException("customerId cannot be blank");
        if (startDate == null || endDate == null) throw new IllegalArgumentException("dates cannot be null");
        if (!endDate.isAfter(startDate)) throw new IllegalArgumentException("endDate must be after startDate");
        if (totalFee <= 0) throw new IllegalArgumentException("totalFee must be > 0");

        this.rentalId = rentalId;
        this.carId = carId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalFee = totalFee;
        this.returned = false;
    }

    public String getRentalId() { return rentalId; }
    public String getCarId() { return carId; }
    public String getCustomerId() { return customerId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getTotalFee() { return totalFee; }
    public boolean isReturned() { return returned; }

    public void markReturned() { this.returned = true; }
}
