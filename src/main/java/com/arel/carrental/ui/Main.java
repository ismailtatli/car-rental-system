package com.arel.carrental.ui;

import com.arel.carrental.inventory.CarInventory;
import com.arel.carrental.io.CsvCarLoader;
import com.arel.carrental.model.*;
import com.arel.carrental.service.RentalService;

import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static String readNonEmpty(Scanner sc, String prompt) {
        System.out.print(prompt);
        String s = sc.nextLine().trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Input cannot be empty.");
        return s;
    }

    private static String readValidFullName(Scanner sc) {
        String name = readNonEmpty(sc, "Full name: ");
        if (!name.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ ]+")) {
            throw new IllegalArgumentException("Invalid name.");
        }
        return name;
    }

    private static int readPositiveInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        int v;
        try {
            v = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid number.");
        }
        if (v <= 0) throw new IllegalArgumentException("Invalid number.");
        return v;
    }

    private static Payment.Method readPaymentMethod(Scanner sc) {
        String s = readNonEmpty(sc, "Payment method (CASH/CARD): ").toUpperCase();
        if (!s.equals("CASH") && !s.equals("CARD")) {
            throw new IllegalArgumentException("Invalid payment method.");
        }
        return Payment.Method.valueOf(s);
    }

    private static String readValidCarId(Scanner sc, CarInventory inventory) {
        String carId = readNonEmpty(sc, "Car ID: ");
        boolean ok = inventory.listAvailableCars().stream()
                .anyMatch(c -> c.getId().equalsIgnoreCase(carId));
        if (!ok) throw new IllegalArgumentException("Car not found or not available.");
        return carId;
    }

    public static void main(String[] args) {

        CarInventory inventory = new CarInventory();
        RentalService service = new RentalService(inventory);

        try {
            CsvCarLoader loader = new CsvCarLoader();
            List<Car> cars = loader.loadCars(Paths.get("data", "cars.csv"));
            for (Car c : cars) inventory.addCar(c);
        } catch (Exception e) {
            System.out.println("CSV error.");
        }

        Scanner sc = new Scanner(System.in);

        Map<String, String> nameToCustomerId = new HashMap<>();
        int customerCounter = 1;

        List<Rental> localRentals = new ArrayList<>();
        String lastRentalId = null;

        while (true) {
            System.out.println("\n1) List available cars");
            System.out.println("2) Rent car");
            System.out.println("3) Return car");
            System.out.println("4) List rentals");
            System.out.println("0) Exit");
            System.out.print("Select: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {

                    case "1": {
                        List<Car> list = inventory.listAvailableCars();
                        if (list.isEmpty()) {
                            System.out.println("No available cars.");
                        } else {
                            for (Car c : list) System.out.println(c);
                        }
                        break;
                    }

                    case "2": {
                        String fullName = readValidFullName(sc);
                        String key = fullName.toLowerCase();

                        String customerId = nameToCustomerId.get(key);
                        if (customerId == null) {
                            customerId = String.format("C%03d", customerCounter++);
                            nameToCustomerId.put(key, customerId);
                            service.registerCustomer(new Customer(customerId, fullName));
                        }

                        String carId = readValidCarId(sc, inventory);
                        int days = readPositiveInt(sc, "Days: ");
                        Payment.Method method = readPaymentMethod(sc);

                        Rental rental = service.rentCar(customerId, carId, days, method);
                        lastRentalId = rental.getRentalId();
                        localRentals.add(rental);

                        System.out.println("Rented. RentalId=" + rental.getRentalId());
                        break;
                    }

                    case "3": {
                        System.out.print("Rental ID (ENTER = last): ");
                        String id = sc.nextLine().trim();
                        if (id.isEmpty()) {
                            if (lastRentalId == null) throw new IllegalStateException("No rental.");
                            id = lastRentalId;
                        }
                        service.returnCar(id);
                        System.out.println("Returned.");
                        break;
                    }

                    case "4": {
                        List<Rental> list = service.listAllRentals();
                        if (list == null || list.isEmpty()) list = localRentals;

                        if (list.isEmpty()) {
                            System.out.println("No rentals.");
                        } else {
                            for (Rental r : list) {
                                System.out.println(r.getRentalId() + " " +
                                        r.getCarId() + " " +
                                        r.getCustomerId() + " " +
                                        r.isReturned());
                            }
                        }
                        break;
                    }

                    case "0":
                        return;

                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}