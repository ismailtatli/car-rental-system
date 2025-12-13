package com.arel.carrental.model;

public class ElectricCar extends Car {

    private final double discountRate;

    public ElectricCar(String id, String brand, String model, double dailyPrice, double discountRate) {
        super(id, brand, model, dailyPrice);
        if (discountRate < 0 || discountRate > 0.5)
            throw new IllegalArgumentException("discountRate must be 0..0.5");
        this.discountRate = discountRate;
    }

    @Override
    public double calculateRentalFee(int days) {
        if (days <= 0) throw new IllegalArgumentException("days must be > 0");
        double base = getDailyPrice() * days;
        return base * (1.0 - discountRate);
    }
}
