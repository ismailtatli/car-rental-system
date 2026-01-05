# OOP Final Project Report - Car Rental System

## UML Diagrams
- Class Diagram: docs/uml/class-diagram.drawio.pdf
- Use Case Diagram: docs/uml/use-case-diagram.drawio.pdf

## Unit Tests (JUnit)
- Tests are located under: src/test/java
- Screenshot: docs/report/screenshots/junit-tests.png

## Git & Github
- Repository: https://github.com/ismailtatli/car-rental-system
- Github Projects (Kanban): https://github.com/users/ismailtatli/projects/3

## Object Oriented Design (OOP)

In this project, I designed the system using OOP principles such as encapsulation, inheritance, polymorphism, and abstraction.
The core rental logic is managed by `RentalService`, while the domain model is represented with separate classes for cars, customers, rentals, and payments.
I used an abstract `Car` class and concrete subclasses (`ElectricCar`, `GasCar`) to apply polymorphism for rental fee calculation.

## Class Responsibilities (Main Classes)

### Car (abstract)
Represents a rentable car with common fields such as id, brand, model and daily price.
It defines a `calculateRentalFee()` method (polymorphic) which is implemented differently by subclasses.

### ElectricCar
A concrete car type that extends `Car`.
It calculates rental fee based on the electric car pricing logic (e.g., base daily price and relevant extra rules).

### GasCar
A concrete car type that extends `Car`.
It calculates rental fee using gas car pricing logic (e.g., base daily price + service fee per day).

### RentalService
This is the main service layer of the application.
It controls the workflow: list available cars, register customers, rent a car, return a car, and create payment records.
It also validates cases such as renting non-existing cars or returning already returned rentals.

## Supporting Classes (Model / Helper)

- `Customer`: Stores customer identity and basic info.
- `Rental`: Holds rental details such as rented car, customer, dates/days, and status.
- `Payment`: Stores payment method and payment amount for a rental.
- `CarInventory`: Manages the list of cars and availability status.
- `Rentable` (interface): Defines the contract for rentable items (implemented by `Car`).
- `Main`: Console UI entry point that shows menu and runs demo flow.
