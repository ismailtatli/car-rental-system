package com.arel.carrental.model;

/**
 * Abstract base class representing a rentable car in the system.
 */
public abstract class Car implements Rentable {


    private final String id;
    private final String brand;
    private final String model;
    private final double dailyPrice;
    private boolean available = true;

    protected Car(String id, String brand, String model, double dailyPrice) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id cannot be blank");
        if (brand == null || brand.isBlank()) throw new IllegalArgumentException("brand cannot be blank");
        if (model == null || model.isBlank()) throw new IllegalArgumentException("model cannot be blank");
        if (dailyPrice <= 0) throw new IllegalArgumentException("dailyPrice must be > 0");

        this.id = id;
        this.brand = brand;
        this.model = model;
        this.dailyPrice = dailyPrice;
    }

    // polymorphism requirement
    public abstract double calculateRentalFee(int days);

    public String getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getDailyPrice() { return dailyPrice; }

    @Override
    public boolean isAvailable() { return available; }

    @Override
    public void rent() {
        if (!available) throw new IllegalStateException("Car is not available");
        available = false;
    }

    @Override
    public void returnCar() {
        available = true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", dailyPrice=" + dailyPrice +
                ", available=" + available +
                '}';
    }
}
