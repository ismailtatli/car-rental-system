package com.arel.carrental.io;

import com.arel.carrental.model.Car;
import com.arel.carrental.model.ElectricCar;
import com.arel.carrental.model.GasCar;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvCarLoader {

    public List<Car> loadCars(Path path) throws IOException {
        List<Car> cars = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (header) { header = false; continue; }

                String[] c = line.split(",", -1);
                String id = c[0].trim();
                String type = c[1].trim();
                String brand = c[2].trim();
                String model = c[3].trim();
                double dailyPrice = Double.parseDouble(c[4].trim());
                double extra = Double.parseDouble(c[5].trim());

                if ("ELECTRIC".equalsIgnoreCase(type)) {
                    cars.add(new ElectricCar(id, brand, model, dailyPrice, extra));
                } else if ("GAS".equalsIgnoreCase(type)) {
                    cars.add(new GasCar(id, brand, model, dailyPrice, extra));
                } else {
                    throw new IllegalArgumentException("Unknown type: " + type);
                }
            }
        }

        return cars;
    }
}