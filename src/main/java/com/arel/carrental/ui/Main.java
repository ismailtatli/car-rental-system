package com.arel.carrental.ui;

import com.arel.carrental.inventory.CarInventory;
import com.arel.carrental.io.CsvCarLoader;
import com.arel.carrental.model.*;
import com.arel.carrental.service.RentalService;

import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        CarInventory inventory = new CarInventory();
        RentalService service = new RentalService(inventory);

        // CSV load
        try {
            CsvCarLoader loader = new CsvCarLoader();
            List<Car> cars = loader.loadCars(Paths.get("data", "cars.csv"));
            for (Car car : cars) inventory.addCar(car);
            System.out.println("Cars loaded: " + cars.size());
        } catch (Exception e) {
            System.out.println("CSV load error: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);

        // Name -> CustomerId mapping (ID otomatik)
        Map<String, String> nameToCustomerId = new HashMap<>();
        int customerCounter = 1;

        // Main tarafında kiralama kayıtları (4'te kesin görünsün diye)
        List<Rental> localRentals = new ArrayList<>();

        String lastRentalId = null;
        String lastCustomerName = null;

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
                    case "1": {
                        List<Car> availableCars = inventory.listAvailableCars();
                        if (availableCars.isEmpty()) {
                            System.out.println("No available cars.");
                        } else {
                            for (Car c : availableCars) System.out.println(c);
                        }
                        break;
                    }

                    case "2": {
                        System.out.print("Full name (e.g., Ismail Tatli): ");
                        String fullName = sc.nextLine().trim();
                        if (fullName.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");

                        // customerId otomatik üret / varsa kullan
                        String customerId = nameToCustomerId.get(fullName.toLowerCase());
                        if (customerId == null) {
                            customerId = String.format("C%03d", customerCounter++);
                            nameToCustomerId.put(fullName.toLowerCase(), customerId);
                            service.registerCustomer(new Customer(customerId, fullName));
                        }

                        System.out.print("Car ID (e.g., E1): ");
                        String carId = sc.nextLine().trim();

                        System.out.print("Days: ");
                        int days = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Payment method (CASH/CARD): ");
                        Payment.Method method = Payment.Method.valueOf(sc.nextLine().trim().toUpperCase());

                        Rental rental = service.rentCar(customerId, carId, days, method);
                        lastRentalId = rental.getRentalId();
                        lastCustomerName = fullName;

                        // local listeye de ekle (4'te garanti görünsün)
                        localRentals.add(rental);

                        System.out.println(fullName + " arabayi basariyla kiraladiniz ✅");
                        System.out.println("RentalId=" + rental.getRentalId() + ", Total=" + rental.getTotalFee());

                        Payment payment = service.getPaymentForRental(rental.getRentalId());
                        if (payment != null) {
                            System.out.println("Payment ✅ Method=" + payment.getMethod() + ", PaidAt=" + payment.getPaidAt());
                        }
                        break;
                    }

                    case "3": {
                        System.out.print("Rental ID (ENTER = last): ");
                        String rentalId = sc.nextLine().trim();
                        if (rentalId.isEmpty()) rentalId = lastRentalId;

                        service.returnCar(rentalId);

                        // local listede de returned flag güncelle (Rental modelinde setter yoksa bu kısım sorun olmaz diye yorumlamadım)
                        System.out.println("RETURNED ✅ RentalId=" + rentalId);
                        break;
                    }

                    case "4": {
                        // önce service’den dene
                        List<Rental> rentalsFromService = service.listAllRentals();

                        List<Rental> toShow = rentalsFromService;
                        if (toShow == null || toShow.isEmpty()) {
                            toShow = localRentals; // garanti
                        }

                        if (toShow.isEmpty()) {
                            System.out.println("No rentals yet.");
                        } else {
                            for (Rental r : toShow) {
                                System.out.println("Rental{" + r.getRentalId() +
                                        ", car=" + r.getCarId() +
                                        ", customer=" + r.getCustomerId() +
                                        ", returned=" + r.isReturned() + "}");
                            }
                        }
                        break;
                    }

                    case "0": {
                        System.out.println("Bye.");
                        return;
                    }

                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}