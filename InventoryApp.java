import java.util.List;
import java.util.Scanner;

public class InventoryApp {
    private static final String DATA_FILE = "inventory.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inventory inventory;
        try {
            inventory = Inventory.loadFromFile(DATA_FILE);
            System.out.println("Loaded inventory from " + DATA_FILE);
        } catch (Exception e) {
            System.out.println("Could not load inventory. Starting fresh.");
            inventory = new Inventory();
        }

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": // Add
                    try {
                        System.out.print("Enter ID (int): ");
                        int id = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Name: ");
                        String name = sc.nextLine().trim();
                        System.out.print("Quantity (int): ");
                        int qty = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Price (double): ");
                        double price = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Supplier: ");
                        String sup = sc.nextLine().trim();

                        Item item = new Item(id, name, qty, price, sup);
                        if (inventory.addItem(item)) {
                            System.out.println("Item added.");
                        } else {
                            System.out.println("Item with this ID already exists.");
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid number format. Try again.");
                    }
                    break;

                case "2": // Update
                    try {
                        System.out.print("Enter ID to update: ");
                        int idu = Integer.parseInt(sc.nextLine().trim());
                        Item it = inventory.getById(idu);
                        if (it == null) {
                            System.out.println("Item not found.");
                            break;
                        }
                        System.out.println("Leave field blank to keep current value.");
                        System.out.println("Current: " + it);

                        System.out.print("New Name: ");
                        String nName = sc.nextLine();
                        System.out.print("New Quantity: ");
                        String nQtyStr = sc.nextLine();
                        System.out.print("New Price: ");
                        String nPriceStr = sc.nextLine();
                        System.out.print("New Supplier: ");
                        String nSup = sc.nextLine();

                        Integer nQty = nQtyStr.isBlank() ? null : Integer.parseInt(nQtyStr.trim());
                        Double nPrice = nPriceStr.isBlank() ? null : Double.parseDouble(nPriceStr.trim());
                        boolean ok = inventory.updateItem(idu,
                                nName.isBlank() ? null : nName,
                                nQty, nPrice,
                                nSup.isBlank() ? null : nSup);
                        if (ok) System.out.println("Item updated.");
                        else System.out.println("Update failed.");
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid number format. Try again.");
                    }
                    break;

                case "3": // Delete
                    try {
                        System.out.print("Enter ID to delete: ");
                        int idd = Integer.parseInt(sc.nextLine().trim());
                        if (inventory.deleteItem(idd)) System.out.println("Deleted.");
                        else System.out.println("Item not found.");
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid ID.");
                    }
                    break;

                case "4": // Search by ID
                    try {
                        System.out.print("Enter ID to search: ");
                        int ids = Integer.parseInt(sc.nextLine().trim());
                        Item found = inventory.getById(ids);
                        if (found != null) System.out.println(found);
                        else System.out.println("Not found.");
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid ID.");
                    }
                    break;

                case "5": // Search by name
                    System.out.print("Enter name query: ");
                    String q = sc.nextLine();
                    List<Item> res = inventory.searchByName(q);
                    if (res.isEmpty()) System.out.println("No items matched.");
                    else res.forEach(System.out::println);
                    break;

                case "6": // List all
                    List<Item> all = inventory.listAll();
                    if (all.isEmpty()) System.out.println("Inventory is empty.");
                    else all.forEach(System.out::println);
                    break;

                case "7": // Save
                    try {
                        inventory.saveToFile(DATA_FILE);
                        System.out.println("Saved to " + DATA_FILE);
                    } catch (Exception e) {
                        System.out.println("Save failed: " + e.getMessage());
                    }
                    break;

                case "8": // Load
                    try {
                        inventory = Inventory.loadFromFile(DATA_FILE);
                        System.out.println("Loaded inventory from file.");
                    } catch (Exception e) {
                        System.out.println("Load failed: " + e.getMessage());
                    }
                    break;

                case "0":
                    // Auto-save before exit
                    try {
                        inventory.saveToFile(DATA_FILE);
                        System.out.println("Saved to " + DATA_FILE + " before exit.");
                    } catch (Exception e) {
                        System.out.println("Could not save before exit: " + e.getMessage());
                    }
                    running = false;
                    System.out.println("Exiting. Bye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

            System.out.println(); // spacing
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("=== Inventory Management ===");
        System.out.println("1. Add Item");
        System.out.println("2. Update Item");
        System.out.println("3. Delete Item");
        System.out.println("4. Search by ID");
        System.out.println("5. Search by Name");
        System.out.println("6. List All Items");
        System.out.println("7. Save Inventory");
        System.out.println("8. Load Inventory");
        System.out.println("0. Exit");
    }
}
