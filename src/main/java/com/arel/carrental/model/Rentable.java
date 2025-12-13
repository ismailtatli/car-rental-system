package com.arel.carrental.model;

public interface Rentable {
    void rent();
    void returnCar();
    boolean isAvailable();
}
