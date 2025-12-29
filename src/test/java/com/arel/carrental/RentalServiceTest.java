package com.arel.carrental;

import com.arel.carrental.inventory.CarInventory;
import com.arel.carrental.model.*;
import com.arel.carrental.service.RentalService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentalServiceTest {

    @Test
    void rentCar_shouldCreateRentalAndPayment_andMakeCarUnavailable() {
        CarInventory inv = new CarInventory();
        RentalService service = new RentalService(inv);

        inv.addCar(new ElectricCar("E1", "Tesla", "Model 3", 1000, 0.10));
        service.registerCustomer(new Customer("C1", "Test User"));

        Rental rental = service.rentCar("C1", "E1", 2, Payment.Method.CASH);

        assertNotNull(rental.getRentalId());
        assertEquals("E1", rental.getCarId());
        assertEquals("C1", rental.getCustomerId());
        assertFalse(inv.getCarById("E1").isAvailable());

        Payment payment = service.getPaymentForRental(rental.getRentalId());
        assertEquals(rental.getRentalId(), payment.getRentalId());
        assertEquals(Payment.Method.CASH, payment.getMethod());
        assertTrue(payment.getAmount() > 0);
    }

    @Test
    void rentCar_whenCarNotAvailable_shouldThrow() {
        CarInventory inv = new CarInventory();
        RentalService service = new RentalService(inv);

        inv.addCar(new GasCar("G1", "BMW", "320i", 800, 50));
        service.registerCustomer(new Customer("C1", "Test User"));

        service.rentCar("C1", "G1", 1, Payment.Method.CARD);

        assertThrows(IllegalStateException.class,
                () -> service.rentCar("C1", "G1", 1, Payment.Method.CARD));
    }

    @Test
    void returnCar_shouldMakeCarAvailable_andMarkRentalReturned() {
        CarInventory inv = new CarInventory();
        RentalService service = new RentalService(inv);

        inv.addCar(new GasCar("G2", "Audi", "A4", 900, 40));
        service.registerCustomer(new Customer("C1", "Test User"));

        Rental rental = service.rentCar("C1", "G2", 3, Payment.Method.CASH);
        assertFalse(inv.getCarById("G2").isAvailable());

        service.returnCar(rental.getRentalId());

        assertTrue(inv.getCarById("G2").isAvailable());
        assertTrue(service.listAllRentals().stream()
                .filter(r -> r.getRentalId().equals(rental.getRentalId()))
                .findFirst()
                .orElseThrow()
                .isReturned());
    }
    @Test
    void returnCar_whenRentalIdNotFound_shouldThrow() {
        CarInventory inv = new CarInventory();
        RentalService service = new RentalService(inv);

        assertThrows(RuntimeException.class,
                () -> service.returnCar("NO_RENTAL"));
    }
    @Test
    void payment_shouldExistForCreatedRental() {
        CarInventory inv = new CarInventory();
        RentalService service = new RentalService(inv);

        inv.addCar(new ElectricCar("E3", "Tesla", "Model S", 1200, 0.15));
        service.registerCustomer(new Customer("C1", "Test User"));

        Rental rental = service.rentCar("C1", "E3", 2, Payment.Method.CARD);

        Payment payment = service.getPaymentForRental(rental.getRentalId());
        assertNotNull(payment);
    }


}

