Inventory Management System
Overview
This project implements a minimal Inventory Management System in Java that supports different item types (Perishable, Electronic, and Clothing) with distinct valuation and handling rules. It demonstrates object-oriented principles such as encapsulation, inheritance, polymorphism (overriding and dynamic dispatch), and overloading, using an abstract base class (InventoryItem) and interfaces (StockTrackable and PriceAdjustable).
Features

Abstract base class InventoryItem with private fields and getters.
Interface StockTrackable for stock operations (receive and issue).
Interface PriceAdjustable for price adjustments (applyDiscount and applySurcharge).
Concrete classes: PerishableItem, ElectronicItem, and ClothingItem.
Service layer InventoryManager for managing a collection of items.
Demo class with sample usage and output.

Requirements

Java 17 or higher.
Maven or Gradle for building and testing (optional, for unit tests).
JUnit 5 (for running unit tests).

Getting Started
Prerequisites

Install Java Development Kit (JDK) 17+.
Install Maven or Gradle if you plan to run tests.

Installation

Clone the repository:git clone <your-repo-url>
cd quiz


Ensure the project structure includes src/main/java and src/test/java directories.

Building and Testing

Using Maven:mvn clean test


Using Gradle:./gradlew clean test


Both commands compile the code and run the unit tests.

Running the Demo

Compile the project:javac -d out/production/quiz src/*.java


Run the demo:java -cp out/production/quiz Demo

This will print a snapshot of the system state, including item details, total value, category quantities, and issue results.

Files

InventoryManagementSystem.java: Main source code containing all classes and the demo.
InventoryManagementSystemTest.java: Unit tests using JUnit 5.
UML/ims-uml.jpg: UML diagram of the project architecture.
README.md: This file.

UML Diagram
The architecture is visualized in UML/ims-uml.jpg. It shows the class hierarchy, interfaces, and relationships between components.
License
This project is for educational purposes only. No specific license is applied; feel free to use and modify as needed.
Acknowledgments

Developed as part of a programming quiz on October 10, 2025.
Thanks to the course instructor for the assignment guidelines.
