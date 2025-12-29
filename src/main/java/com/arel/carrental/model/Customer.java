package com.arel.carrental.model;
/**
 * Represents a customer who can rent cars from the system.
 */
public class Customer {

    private final String id;
    private final String fullName;

    public Customer(String id, String fullName) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id cannot be blank");
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("fullName cannot be blank");
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }

    @Override
    public String toString() {
        return "Customer{id='" + id + "', fullName='" + fullName + "'}";
    }
}
