package com.arel.carrental.model;

/**
 * Defines common behaviors for rentable entities.
 */
public interface Rentable {

    void rent();
    void returnCar();
    boolean isAvailable();
}
