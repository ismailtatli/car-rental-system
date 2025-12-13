package com.arel.carrental.ui;

import com.arel.carrental.inventory.CarInventory;
import com.arel.carrental.model.ElectricCar;
import com.arel.carrental.model.GasCar;

public class Main {
    public static void main(String[] args) {
        CarInventory inventory = new CarInventory();

        inventory.addCar(new ElectricCar("E1", "Tesla", "Model 3", 1000, 0.10));
        inventory.addCar(new GasCar("G1", "BMW", "320i", 800, 50));

        System.out.println("=== All Cars ===");
        inventory.listAllCars().forEach(System.out::println);

        System.out.println("\n=== Available Cars ===");
        inventory.listAvailableCars().forEach(System.out::println);
    }
}
