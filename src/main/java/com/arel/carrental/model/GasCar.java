package com.arel.carrental.model;

/**
 * Represents a gasoline car with an additional daily service cost.
 */
public class GasCar extends Car {


    private final double serviceFeePerDay;

    public GasCar(String id, String brand, String model, double dailyPrice, double serviceFeePerDay) {
        super(id, brand, model, dailyPrice);
        if (serviceFeePerDay < 0) throw new IllegalArgumentException("serviceFeePerDay cannot be negative");
        this.serviceFeePerDay = serviceFeePerDay;
    }

    @Override
    public double calculateRentalFee(int days) {
        if (days <= 0) throw new IllegalArgumentException("days must be > 0");
        return (getDailyPrice() * days) + (serviceFeePerDay * days);
    }
}
