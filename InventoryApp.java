import java.io.*;
import java.util.*;

public class InventoryApp {

static class Product implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { return id; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }

    public String toString() {
        return "ID: " + id +  ", Name: " + name + ", Quantity: " + quantity + ", Price: " + price;
    }
}

static final String FileName = "System Inventory.dat";
static ArrayList<Product> products = new ArrayList<>();
static Scanner sc = new Scanner(System.in);

public static void main(String[] args) {
    loadFromFile();

    while (true) {
        System.out.println("\n=== INVENTORY SYSTEM ===");
        System.out.println("1. Add Product");
        System.out.println("2. View Products");
        System.out.println("3. Update Product");
        System.out.println("4. Exit");
        System.out.print("Choose: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                addProduct();
                break;
            case 2:
                viewProducts();
                break;
            case 3:
                updateProduct();
                break;
            case 4:
                saveToFile();
                System.out.println("\nGoodbye!");
                return;
            default:
                System.out.println("\nInvalid choice!");
                break;
        }
    }
}

static void addProduct() {
    System.out.print("Enter ID: ");
    int id = sc.nextInt();
    sc.nextLine();

    for (Product p : products) {
        if (p.getId() == id) {
            System.out.println("\nProduct ID existed, try again!");
            return;
        }
    }

    System.out.print("Enter Name: ");
    String name = sc.nextLine();

    System.out.print("Enter Quantity: ");
    int qty = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter Price: ");
    double price = sc.nextDouble();
    sc.nextLine();

    products.add(new Product(id, name, qty, price));
    saveToFile();
    System.out.println("\nAdded product successful!");
}

static void viewProducts() {
    if (products.isEmpty()) {
        System.out.println("\nInventory is empty.");
    } else {
        System.out.println("\n=== ALL PRODUCTS ===");
        for (Product p : products) {
            System.out.println(p);
        }
    }
}

static void updateProduct() {
    System.out.print("Enter Product ID to update: ");
    int id = sc.nextInt();
    sc.nextLine();

    for (Product p : products) {
        if (p.getId() == id) {
            System.out.print("New Name: ");
            String name = sc.nextLine();
            p.setName(name);

            System.out.print("New Quantity: ");
            int qty = sc.nextInt();
            sc.nextLine();
            p.setQuantity(qty);

            System.out.print("New Price: ");
            double price = sc.nextDouble();
            sc.nextLine();
            p.setPrice(price);

            saveToFile();
            System.out.println("Product updated!");
            return;
        }
    }
        System.out.println("Product not found.");
    }

    static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileName))) {
           oos.writeObject(products);
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    static void loadFromFile() {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileName))) {
         products = (ArrayList<Product>) ois.readObject();
      } catch (Exception e) {
          products = new ArrayList<>();
        }
    }
}