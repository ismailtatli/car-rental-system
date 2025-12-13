package com.arel.carrental.ui;

import com.arel.carrental.inventory.CarInventory;
import com.arel.carrental.model.*;
import com.arel.carrental.service.RentalService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CarInventory inventory = new CarInventory();
        RentalService service = new RentalService(inventory);

        // Demo seed
        inventory.addCar(new ElectricCar("E1", "Tesla", "Model 3", 1000, 0.10));
        inventory.addCar(new GasCar("G1", "BMW", "320i", 800, 50));
        service.registerCustomer(new Customer("C1", "Ismail Tatli"));

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Car Rental System ===");
            System.out.println("1) List available cars");
            System.out.println("2) Rent car");
            System.out.println("3) Return car");
            System.out.println("4) List all rentals");
            System.out.println("0) Exit");
            System.out.print("Select: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> inventory.listAvailableCars().forEach(System.out::println);

                    case "2" -> {
                        System.out.print("Customer ID (e.g., C1): ");
                        String customerId = sc.nextLine().trim();

                        System.out.print("Car ID (e.g., E1): ");
                        String carId = sc.nextLine().trim();

                        System.out.print("Days: ");
                        int days = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Payment method (CASH/CARD): ");
                        Payment.Method method = Payment.Method.valueOf(sc.nextLine().trim().toUpperCase());

                        Rental rental = service.rentCar(customerId, carId, days, method);
                        Payment payment = service.getPaymentForRental(rental.getRentalId());

                        System.out.println("RENTED ✅ RentalId=" + rental.getRentalId() + ", Total=" + rental.getTotalFee());
                        System.out.println("PAYMENT ✅ Method=" + payment.getMethod() + ", PaidAt=" + payment.getPaidAt());
                    }

                    case "3" -> {
                        System.out.print("Rental ID: ");
                        String rentalId = sc.nextLine().trim();
                        service.returnCar(rentalId);
                        System.out.println("RETURNED ✅");
                    }

                    case "4" -> service.listAllRentals().forEach(r ->
                            System.out.println("Rental{" + r.getRentalId() +
                                    ", car=" + r.getCarId() +
                                    ", customer=" + r.getCustomerId() +
                                    ", returned=" + r.isReturned() + "}")
                    );

                    case "0" -> {
                        System.out.println("Bye.");
                        return;
                    }

                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
