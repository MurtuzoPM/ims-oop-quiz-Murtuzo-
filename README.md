Inventory Management System (IMS) - OOP Quiz
Student Name: Mamadziyoev Murtuzo
Course: Object-Oriented Programming
Assignment: IMS Quiz Project

Project Overview
This project implements a minimal Inventory Management System that demonstrates core OOP principles including encapsulation, inheritance, polymorphism, and abstraction. The system supports different item types (Perishable, Electronic, Clothing) with different valuation and handling rules, and exposes uniform stock operations through interfaces.

System Requirements
Java Version: Java 11 or higher (tested with Java 17)
Build Tool: Maven 3.6+ (or Gradle 7+)
Testing Framework: JUnit 5.9.3
Project Structure
ims-oop-quiz-<YourFullName>/
├── src/
│   ├── main/java/com/ec/ims/
│   │   ├── InventoryItem.java           # Abstract base class
│   │   ├── StockTrackable.java          # Interface for stock operations
│   │   ├── PriceAdjustable.java         # Interface for pricing rules
│   │   ├── PerishableItem.java          # Concrete: food items with shelf life
│   │   ├── ElectronicItem.java          # Concrete: electronics with warranty
│   │   ├── ClothingItem.java            # Concrete: clothing with size/material
│   │   ├── InventoryManager.java        # Service layer for inventory operations
│   │   └── Demo.java                    # Demo application
│   └── test/java/com/ec/ims/
│       ├── InventoryItemTest.java
│       ├── PerishableItemTest.java
│       ├── ElectronicItemTest.java
│       ├── ClothingItemTest.java
│       └── InventoryManagerTest.java
├── UML/
│   └── ims-uml.jpg                      # Hand-drawn UML class diagram
├── pom.xml                              # Maven configuration
└── README.md                            # This file
How to Build and Run
Build the Project
Using Maven:

bash
mvn clean compile
Using Gradle:

bash
./gradlew clean build
Run Tests
Using Maven:

bash
mvn clean test
Using Gradle:

bash
./gradlew test
Run Demo Application
Using Maven:

bash
mvn exec:java -Dexec.mainClass="com.ec.ims.Demo"
Using Gradle (if configured):

bash
gradle run
Or compile and run manually:

bash
javac -d bin src/main/java/com/ec/ims/*.java
java -cp bin com.ec.ims.Demo
Design Overview
Core OOP Principles Demonstrated
1. Encapsulation
All fields in classes are private
Access controlled through public getters and protected setters
Validation in constructors and setters (e.g., negative quantities/prices throw IllegalArgumentException)
2. Abstraction
Abstract class: InventoryItem defines common structure and behavior
Abstract method: category() must be implemented by subclasses
Interfaces: StockTrackable and PriceAdjustable define contracts
3. Inheritance
PerishableItem, ElectronicItem, and ClothingItem extend InventoryItem
Each subclass adds specific attributes (e.g., shelfLifeDays, warrantyMonths, size/material)
Each overrides category() and toString()
4. Polymorphism
Method Overriding: Each subclass provides its own implementation of category() and toString()
Dynamic Dispatch: InventoryManager operates on List<InventoryItem> and calls overridden methods
Interface Implementation: All concrete items implement StockTrackable; at least one implements PriceAdjustable
Method Overloading: InventoryManager.issue(String sku, int qty) and issue(InventoryItem item, int qty)
5. Additional Design Choices
Final method: value() in InventoryItem is marked final to prevent overriding (business rule enforcement)
Protected methods: setQuantity() allows controlled mutation by subclasses
Class Responsibilities
InventoryItem (Abstract)
Defines common attributes: sku, name, quantity, unitPrice
Provides value() calculation (quantity × unitPrice)
Enforces validation rules
Declares abstract category() method
StockTrackable (Interface)
receive(int qty): Increases stock
issue(int qty): Decreases stock if sufficient; returns success/failure
PriceAdjustable (Interface)
applyDiscount(double percent): Reduces unit price (0-100%)
applySurcharge(double percent): Increases unit price (0-100%)
PerishableItem
Adds shelfLifeDays attribute
Implements both StockTrackable and PriceAdjustable
Category: "Perishable"
ElectronicItem
Adds warrantyMonths attribute
Implements both StockTrackable and PriceAdjustable
Category: "Electronic"
ClothingItem
Adds size and material attributes
Implements StockTrackable and PriceAdjustable
Category: "Clothing"
InventoryManager
Manages collection of InventoryItem objects
Provides:
addItem(): Add item to inventory
findBySku(): Search by SKU
totalValue(): Sum of all item values (polymorphic)
totalQuantityByCategory(): Count items by category (case-insensitive)
issue(): Overloaded methods for issuing stock
Testing Coverage
All JUnit 5 tests cover:

✅ Encapsulation & Validation: Invalid arguments throw IllegalArgumentException
✅ Core Behaviors: Receive, issue, price adjustments work correctly
✅ Polymorphism: Manager operations work with mixed item types
✅ Edge Cases:
Issuing more than available stock returns false
Discount/surcharge boundaries (0%, 100%)
Searching for non-existent SKU returns null
Negative values rejected
Test Execution
Run mvn test and verify all tests pass with green status.

Known Limitations & Assumptions
No Persistence: All data is in-memory; no database or file storage
No Concurrency: Not thread-safe (single-threaded use assumed)
SKU Uniqueness: Not enforced by InventoryManager (assumes caller manages duplicates)
Case Sensitivity: findBySku() is case-sensitive; totalQuantityByCategory() is case-insensitive
Price Precision: Uses double for prices (in production, consider BigDecimal for financial accuracy)
Validation: Basic validation only (e.g., no max quantity limits, no format checks on SKU)
UML Diagram
The UML class diagram is located in UML/ims-uml.jpg (hand-drawn, scanned/photographed).

Key relationships shown:

Inheritance: Concrete items extend InventoryItem (solid line, hollow triangle)
Realization: Concrete items implement interfaces (dashed line, hollow triangle)
Association: InventoryManager → InventoryItem (multiplicity: *)
Demo Output Example
When running Demo.java, you should see output similar to:

=== Inventory Management System Demo ===

--- Adding Items ---
Added: MILK-001/Fresh Milk/Perishable/qty=0/unitPrice=2.50/value=0.00/shelfLife=7 days
Added: LAPTOP-001/Gaming Laptop/Electronic/qty=0/unitPrice=1200.00/value=0.00/warranty=24 months
Added: TSHIRT-001/Cotton T-Shirt/Clothing/qty=0/unitPrice=15.00/value=0.00/size=M/material=Cotton

--- Receiving Stock ---
Received 50 units of MILK-001
Received 10 units of LAPTOP-001
Received 100 units of TSHIRT-001

--- Current Inventory ---
MILK-001/Fresh Milk/Perishable/qty=50/unitPrice=2.50/value=125.00/shelfLife=7 days
LAPTOP-001/Gaming Laptop/Electronic/qty=10/unitPrice=1200.00/value=12000.00/warranty=24 months
TSHIRT-001/Cotton T-Shirt/Clothing/qty=100/unitPrice=15.00/value=1500.00/size=M/material=Cotton

Total Inventory Value: $13625.00

--- Category Totals ---
Perishable items: 50
Electronic items: 10
Clothing items: 100

--- Applying Discounts ---
Applied 20% discount to MILK-001
Applied 10% discount to LAPTOP-001

--- Issuing Stock ---
Issued 30 units of MILK-001: true
Issued 5 units of LAPTOP-001: true
Attempted to issue 200 units of TSHIRT-001: false (insufficient stock)

--- Final Inventory ---
MILK-001/Fresh Milk/Perishable/qty=20/unitPrice=2.00/value=40.00/shelfLife=7 days
LAPTOP-001/Gaming Laptop/Electronic/qty=5/unitPrice=1080.00/value=5400.00/warranty=24 months
TSHIRT-001/Cotton T-Shirt/Clothing/qty=100/unitPrice=15.00/value=1500.00/size=M/material=Cotton

Final Total Value: $6940.00
GitHub Repository
Repository URL: [https://github.com/YourUsername/ims-oop-quiz-YourFullName]

Ensure the repository is public and contains all required files before submission.

Submission Checklist
✅ All source files in correct package structure
✅ All JUnit tests pass (mvn test succeeds)
✅ Demo application runs and produces output
✅ README.md complete with build instructions
✅ UML diagram in UML/ims-uml.jpg (hand-drawn, scanned)
✅ pom.xml or build.gradle configured correctly
✅ Frequent commits with meaningful messages
✅ GitHub repository is public
✅ ZIP file mirrors repository structure

License: Educational use only - OOP Quiz Assignment

