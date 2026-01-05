# Car Rental System – Report Notes (Draft)

## Overview
This project is a console-based Car Rental System developed for the Object Oriented Programming course.
The system allows listing available cars, renting a car, returning a car, and tracking payments.

## OOP Design Summary
- Inheritance: `Car` is an abstract class, extended by `ElectricCar` and `GasCar`.
- Interface: `Rentable` defines rental-related behavior.
- Encapsulation: model classes keep fields private and expose required getters/setters.
- Polymorphism: rental fee calculation uses overridden implementations in subclasses.

## Main Classes (Short)
- **Car (abstract)**: base class for all cars, includes common fields and `calculateRentalFee()`.
- **ElectricCar / GasCar**: specialized car types with different fee logic.
- **CarInventory**: holds cars and tracks availability.
- **Customer**: stores customer info.
- **Rental**: represents a rental transaction.
- **Payment**: stores payment details.
- **RentalService**: main business logic for rent/return and payment records.
- **Main**: console menu to run the demo.

## UML
UML Class and Use Case diagrams are provided under `docs/uml/`.

## Testing
JUnit tests are implemented under `src/test/java` to validate rental workflow, return logic, and payment validation.
A screenshot of test results will be included in the final report document.

## Git & Kanban
This project was developed with meaningful commits and tracked using GitHub Projects (Kanban board).




## Final Notes
- Final checks completed (UML PDFs, JUnit tests screenshot, Git log screenshot).