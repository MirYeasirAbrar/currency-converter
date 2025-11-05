import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Seed data
        List<User> users = new ArrayList<>();
        List<MenuItem> menuItems = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        int[] userIdCounter = {1};
        int[] menuIdCounter = {1};
        int[] orderIdCounter = {1};

        // Default admin
        users.add(new User(userIdCounter[0]++, "admin", "admin123", "ADMIN", true, "System Admin"));

        // Sample menu
        menuItems.add(new MenuItem(menuIdCounter[0]++, "Margherita Pizza", "Pizza", 9.99, true));
        menuItems.add(new MenuItem(menuIdCounter[0]++, "Veggie Burger", "Burger", 7.49, true));
        menuItems.add(new MenuItem(menuIdCounter[0]++, "Caesar Salad", "Salad", 5.99, true));
        menuItems.add(new MenuItem(menuIdCounter[0]++, "Spaghetti Bolognese", "Pasta", 8.99, true));

        // App loop
        while (true) {
            BaseSystem.printHeader("🍽️ Drive-Thru Restaurant");
            System.out.println("[1] Login");
            System.out.println("[2] Sign Up (Customer)");
            System.out.println("[3] Exit");
            System.out.print("Choose: ");
            int choice = BaseSystem.readInt(sc);

            switch (choice) {
                case 1 -> {
                    User user = BaseSystem.login(sc, users);
                    if (user != null) {
                        if ("ADMIN".equals(user.getRole())) {
                            new AdminSystem(user, menuItems, orders, menuIdCounter).showAdminMenu(sc);
                        } else {
                            new CustomerSystem(user, menuItems, orders, orderIdCounter).showCustomerMenu(sc);
                        }
                    }
                }
                case 2 -> {
                    User user = BaseSystem.signUp(sc, users, userIdCounter);
                    if (user != null) {
                        new CustomerSystem(user, menuItems, orders, orderIdCounter).showCustomerMenu(sc);
                    }
                }
                case 3 -> {
                    System.out.println("👋 Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("⚠️ Invalid choice!");
            }
        }
    }
}
