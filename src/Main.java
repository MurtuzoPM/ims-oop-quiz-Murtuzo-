import java.util.ArrayList;
import java.util.List;

abstract class InventoryItem {
    private String sku;
    private String name;
    private int quantity;
    private double unitPrice;

    protected InventoryItem(String sku, String name, double unitPrice) {
        if (sku == null || sku.trim().isEmpty() || name == null || name.trim().isEmpty() || unitPrice < 0) {
            throw new IllegalArgumentException("Invalid input: SKU, name, or unitPrice");
        }
        this.sku = sku;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = 0;
    }

    public String getSku() { return sku; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }

    protected void setQuantity(int qty) {
        if (qty < 0) throw new IllegalArgumentException("Quantity cannot be negative");
        this.quantity = qty;
    }

    // New protected method to set unitPrice with validation
    protected void setUnitPrice(double unitPrice) {
        if (unitPrice < 0) throw new IllegalArgumentException("Unit price cannot be negative");
        this.unitPrice = unitPrice;
    }

    public final double value() {
        return quantity * unitPrice;
    }

    public abstract String category();

    @Override
    public String toString() {
        return sku + "/" + name + "/" + category() + "/qty=" + quantity + "/unitPrice=" + unitPrice + "/value=" + value();
    }
}

interface StockTrackable {
    void receive(int qty);
    boolean issue(int qty);
}

interface PriceAdjustable {
    void applyDiscount(double percent);
    void applySurcharge(double percent);
}

class PerishableItem extends InventoryItem implements StockTrackable {
    private int shelfLifeDays;

    public PerishableItem(String sku, String name, double unitPrice, int shelfLifeDays) {
        super(sku, name, unitPrice);
        if (shelfLifeDays < 0) throw new IllegalArgumentException("Shelf life cannot be negative");
        this.shelfLifeDays = shelfLifeDays;
    }

    @Override
    public String category() { return "Perishable"; }

    @Override
    public void receive(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");
        setQuantity(getQuantity() + qty);
    }

    @Override
    public boolean issue(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (getQuantity() >= qty) {
            setQuantity(getQuantity() - qty);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "/shelfLifeDays=" + shelfLifeDays;
    }
}

class ElectronicItem extends InventoryItem implements StockTrackable, PriceAdjustable {
    private int warrantyMonths;

    public ElectronicItem(String sku, String name, double unitPrice, int warrantyMonths) {
        super(sku, name, unitPrice);
        if (warrantyMonths < 0) throw new IllegalArgumentException("Warranty cannot be negative");
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String category() { return "Electronic"; }

    @Override
    public void receive(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");
        setQuantity(getQuantity() + qty);
    }

    @Override
    public boolean issue(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (getQuantity() >= qty) {
            setQuantity(getQuantity() - qty);
            return true;
        }
        return false;
    }

    @Override
    public void applyDiscount(double percent) {
        if (percent < 0 || percent > 100) throw new IllegalArgumentException("Discount must be between 0 and 100");
        double newPrice = getUnitPrice() * (1 - percent / 100);
        setUnitPrice(newPrice);
    }

    @Override
    public void applySurcharge(double percent) {
        if (percent < 0 || percent > 100) throw new IllegalArgumentException("Surcharge must be between 0 and 100");
        double newPrice = getUnitPrice() * (1 + percent / 100);
        setUnitPrice(newPrice);
    }

    @Override
    public String toString() {
        return super.toString() + "/warrantyMonths=" + warrantyMonths;
    }
}

class ClothingItem extends InventoryItem implements StockTrackable {
    private String size;
    private String material;

    public ClothingItem(String sku, String name, double unitPrice, String size, String material) {
        super(sku, name, unitPrice);
        if (size == null || size.trim().isEmpty() || material == null || material.trim().isEmpty()) {
            throw new IllegalArgumentException("Size and material cannot be null or empty");
        }
        this.size = size;
        this.material = material;
    }

    @Override
    public String category() { return "Clothing"; }

    @Override
    public void receive(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");
        setQuantity(getQuantity() + qty);
    }

    @Override
    public boolean issue(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (getQuantity() >= qty) {
            setQuantity(getQuantity() - qty);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "/size=" + size + "/material=" + material;
    }
}

class InventoryManager {
    private final List<InventoryItem> items = new ArrayList<>();

    public void addItem(InventoryItem item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        items.add(item);
    }

    public InventoryItem findBySku(String sku) {
        if (sku == null || sku.trim().isEmpty()) throw new IllegalArgumentException("SKU cannot be null or empty");
        return items.stream().filter(i -> i.getSku().equals(sku)).findFirst().orElse(null);
    }

    public double totalValue() {
        return items.stream().mapToDouble(InventoryItem::value).sum();
    }

    public int totalQuantityByCategory(String category) {
        if (category == null || category.trim().isEmpty()) throw new IllegalArgumentException("Category cannot be null or empty");
        return (int) items.stream().filter(i -> i.category().equalsIgnoreCase(category)).mapToInt(InventoryItem::getQuantity).sum();
    }

    public boolean issue(String sku, int qty) {
        InventoryItem item = findBySku(sku);
        return item != null && ((StockTrackable) item).issue(qty);
    }

    public boolean issue(InventoryItem item, int qty) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        return ((StockTrackable) item).issue(qty);
    }
}

class Demo {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();

        // Create objects of each item type
        PerishableItem pItem = new PerishableItem("P1", "Apple", 0.5, 10);
        ElectronicItem eItem = new ElectronicItem("E1", "Phone", 200.0, 12);
        ClothingItem cItem = new ClothingItem("C1", "Shirt", 20.0, "M", "Cotton");

        // Receive stock
        pItem.receive(100);
        eItem.receive(50);
        cItem.receive(30);

        // Add to InventoryManager
        manager.addItem(pItem);
        manager.addItem(eItem);
        manager.addItem(cItem);

        // Apply price changes
        ((PriceAdjustable) eItem).applyDiscount(10); // 10% discount

        // Print details
        System.out.println("Items:");
        System.out.println(pItem.toString());
        System.out.println(eItem.toString());
        System.out.println(cItem.toString());
        System.out.println("Total Value: " + manager.totalValue());
        System.out.println("Total Quantity by Perishable: " + manager.totalQuantityByCategory("Perishable"));
        System.out.println("Issue 60 from P1: " + manager.issue("P1", 60));
        System.out.println("Issue 20 from P1: " + manager.issue("P1", 20));
        System.out.println("Issue 21 from P1: " + manager.issue("P1", 21)); // Should fail
    }
}