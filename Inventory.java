import java.io.*;
import java.util.*;

public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Integer, Item> items = new HashMap<>();

    // Add item (returns false if id exists)
    public boolean addItem(Item item) {
        if (items.containsKey(item.getId())) return false;
        items.put(item.getId(), item);
        return true;
    }

    // Update item (returns true if update successful)
    public boolean updateItem(int id, String name, Integer quantity, Double price, String supplier) {
        Item it = items.get(id);
        if (it == null) return false;
        if (name != null && !name.isBlank()) it.setName(name);
        if (quantity != null) it.setQuantity(quantity);
        if (price != null) it.setPrice(price);
        if (supplier != null) it.setSupplier(supplier);
        return true;
    }

    // Delete item
    public boolean deleteItem(int id) {
        return items.remove(id) != null;
    }

    // Search by id
    public Item getById(int id) {
        return items.get(id);
    }

    // Search by name (contains, case-insensitive)
    public List<Item> searchByName(String query) {
        List<Item> res = new ArrayList<>();
        String q = query.toLowerCase();
        for (Item i : items.values()) {
            if (i.getName().toLowerCase().contains(q)) res.add(i);
        }
        return res;
    }

    // List all items
    public List<Item> listAll() {
        List<Item> list = new ArrayList<>(items.values());
        list.sort(Comparator.comparingInt(Item::getId));
        return list;
    }

    // Save to file
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

    // Load from file (static helper)
    public static Inventory loadFromFile(String filename) throws IOException, ClassNotFoundException {
        File f = new File(filename);
        if (!f.exists()) return new Inventory();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof Inventory) {
                return (Inventory) obj;
            } else {
                throw new IOException("File does not contain Inventory data.");
            }
        }
    }
}
