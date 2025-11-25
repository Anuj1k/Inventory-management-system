import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int quantity;
    private double price;
    private String supplier;

    public Item(int id, String name, int quantity, double price, String supplier) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplier = supplier;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Qty: %d | Price: %.2f | Supplier: %s",
                id, name, quantity, price, supplier);
    }
}
