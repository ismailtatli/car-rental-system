package com.arel.carrental.inventory;

import com.arel.carrental.model.Car;

import java.util.*;

public class CarInventory {

    private final Map<String, Car> cars = new HashMap<>();

    public void addCar(Car car) {
        Objects.requireNonNull(car, "car");
        if (cars.containsKey(car.getId())) throw new IllegalArgumentException("Car id already exists: " + car.getId());
        cars.put(car.getId(), car);
    }

    public void removeCar(String carId) {
        if (!cars.containsKey(carId)) throw new NoSuchElementException("Car not found: " + carId);
        cars.remove(carId);
    }

    public Car getCarById(String carId) {
        Car car = cars.get(carId);
        if (car == null) throw new NoSuchElementException("Car not found: " + carId);
        return car;
    }

    public List<Car> listAllCars() {
        List<Car> list = new ArrayList<>(cars.values());
        list.sort(Comparator.comparing(Car::getId));
        return list;
    }

    public List<Car> listAvailableCars() {
        List<Car> list = new ArrayList<>();
        for (Car c : cars.values()) if (c.isAvailable()) list.add(c);
        list.sort(Comparator.comparing(Car::getId));
        return list;
    }
}
