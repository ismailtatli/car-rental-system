package com.arel.carrental.service;

import com.arel.carrental.inventory.CarInventory;
import com.arel.carrental.model.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Manages rent/return workflow and stores Rental/Payment records for demo.
 */
public class RentalService {

    private final CarInventory inventory;

    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Rental> rentals = new HashMap<>();
    private final Map<String, Payment> paymentsByRentalId = new HashMap<>();

    public RentalService(CarInventory inventory) {
        this.inventory = Objects.requireNonNull(inventory, "inventory");
    }

    public void registerCustomer(Customer customer) {
        Objects.requireNonNull(customer, "customer");
        customers.put(customer.getId(), customer);
    }

    public Customer getCustomer(String customerId) {
        Customer c = customers.get(customerId);
        if (c == null) throw new NoSuchElementException("Customer not found: " + customerId);
        return c;
    }

    public Rental rentCar(String customerId, String carId, int days, Payment.Method method) {
        if (days <= 0) throw new IllegalArgumentException("days must be > 0");
        Objects.requireNonNull(method, "method");

        // Validate customer exists
        getCustomer(customerId);

        Car car = inventory.getCarById(carId);
        if (!car.isAvailable()) throw new IllegalStateException("Car is not available: " + carId);

        car.rent();

        double fee = car.calculateRentalFee(days);
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(days);

        String rentalId = UUID.randomUUID().toString();
        Rental rental = new Rental(rentalId, carId, customerId, start, end, fee);
        rentals.put(rentalId, rental);

        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, rentalId, fee, method);
        paymentsByRentalId.put(rentalId, payment);

        return rental;
    }

    public void returnCar(String rentalId) {
        Rental rental = rentals.get(rentalId);
        if (rental == null) throw new NoSuchElementException("Rental not found: " + rentalId);
        if (rental.isReturned()) throw new IllegalStateException("Rental already returned: " + rentalId);

        Car car = inventory.getCarById(rental.getCarId());
        car.returnCar();
        rental.markReturned();
    }

    public Payment getPaymentForRental(String rentalId) {
        Payment p = paymentsByRentalId.get(rentalId);
        if (p == null) throw new NoSuchElementException("Payment not found for rental: " + rentalId);
        return p;
    }

    public List<Rental> listAllRentals() {
        return new ArrayList<>(rentals.values());
    }
}
