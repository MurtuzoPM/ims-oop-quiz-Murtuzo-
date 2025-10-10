# Inventory Management System (IMS) — OOP Quiz Project

## Overview  
This project implements a minimal Inventory Management System in Java that supports different item types (Perishable, Electronic, and Clothing) with distinct valuation and handling rules. It demonstrates object-oriented principles such as encapsulation, inheritance, polymorphism (overriding and dynamic dispatch), and overloading, using an abstract base class (`InventoryItem`) and interfaces (`StockTrackable` and `PriceAdjustable`).

## Features  
- Abstract base class `InventoryItem` with private fields and getters  
- Interface `StockTrackable` for stock operations (receive and issue)  
- Interface `PriceAdjustable` for price adjustments (applyDiscount and applySurcharge)  
- Concrete classes: `PerishableItem`, `ElectronicItem`, and `ClothingItem`  
- Service layer `InventoryManager` for managing a collection of items  
- Demo class with sample usage and output  

## Requirements  
- Java 17 or higher  
- Maven or Gradle (optional, for building / testing)  
- JUnit 5 (for running unit tests)  

## Getting Started

### Prerequisites  
Install JDK 17+  
(Optional) Install Maven or Gradle if you want to build or test via those tools

### Installation & Setup  
```bash
git clone https://github.com/MurtuzoPM/ims-oop-quiz-Murtuzo-.git
cd ims-oop-quiz-Murtuzo-
