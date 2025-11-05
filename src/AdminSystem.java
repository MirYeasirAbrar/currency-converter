import java.util.List;
import java.util.Scanner;

public class AdminSystem {
    private User admin;
    private List<MenuItem> menuItems;
    private List<Order> orders;
    private int[] menuIdCounter;

    public AdminSystem(User admin, List<MenuItem> menuItems, List<Order> orders, int[] menuIdCounter) {
        this.admin = admin;
        this.menuItems = menuItems;
        this.orders = orders;
        this.menuIdCounter = menuIdCounter;
    }

    public void showAdminMenu(Scanner sc) {
        while (true) {
            BaseSystem.printHeader("🛠️ Admin Menu");
            System.out.println("[1] Manage Menu");
            System.out.println("[2] View Orders");
            System.out.println("[3] Logout");
            System.out.print("Choose: ");
            int choice = BaseSystem.readInt(sc);

            switch (choice) {
                case 1 -> manageMenu(sc);
                case 2 -> viewOrders(sc);
                case 3 -> { return; }
                default -> System.out.println("⚠️ Invalid choice!");
            }
        }
    }

    private void manageMenu(Scanner sc) {
        while (true) {
            BaseSystem.printHeader("📋 Menu Management");
            for (MenuItem m : menuItems) System.out.println(m);
            System.out.println("[1] Add Item  \n[2] Edit Item  \n[3] Delete Item  \n[4] Toggle Availability  \n[5] Back");
            System.out.print("Choose: ");
            int choice = BaseSystem.readInt(sc);

            switch (choice) {
                case 1 -> addMenuItem(sc);
                case 2 -> editMenuItem(sc);
                case 3 -> deleteMenuItem(sc);
                case 4 -> toggleAvailability(sc);
                case 5 -> { return; }
                default -> System.out.println("⚠️ Invalid choice!");
            }
        }
    }

    private void addMenuItem(Scanner sc) {
        String name = BaseSystem.readNonEmpty(sc, "Name: ");
        String category = BaseSystem.readNonEmpty(sc, "Category: ");
        System.out.print("Price: ");
        double price = BaseSystem.readDouble(sc);
        menuItems.add(new MenuItem(menuIdCounter[0]++, name, category, price, true));
        System.out.println("✅ Item added!");
    }

    private void editMenuItem(Scanner sc) {
        System.out.print("Enter Menu ID to edit: ");
        int id = BaseSystem.readInt(sc);
        MenuItem item = menuItems.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
        if (item == null) { System.out.println("❌ Item not found."); return; }

        String name = BaseSystem.readNonEmpty(sc, "New name (Enter to skip): ");
        String category = BaseSystem.readNonEmpty(sc, "New category (Enter to skip): ");
        System.out.print("New price (0 to skip): ");
        double price = BaseSystem.readDouble(sc);

        if (!name.isEmpty()) item.setName(name);
        if (!category.isEmpty()) item.setCategory(category);
        if (price > 0) item.setPrice(price);
        System.out.println("✅ Item updated!");
    }

    private void deleteMenuItem(Scanner sc) {
        System.out.print("Enter Menu ID to delete: ");
        int id = BaseSystem.readInt(sc);
        menuItems.removeIf(m -> m.getId() == id);
        System.out.println("❌ Item deleted!");
    }

    private void toggleAvailability(Scanner sc) {
        System.out.print("Enter Menu ID to toggle availability: ");
        int id = BaseSystem.readInt(sc);
        MenuItem item = menuItems.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
        if (item == null) { System.out.println("❌ Item not found."); return; }
        item.setAvailable(!item.isAvailable());
        System.out.println("✅ Availability toggled!");
    }

    private void viewOrders(Scanner sc) {
        BaseSystem.printHeader("📦 All Orders");
        if (orders.isEmpty()) { System.out.println("(none)"); return; }
        for (Order o : orders) System.out.println(o.brief());
        System.out.print("Enter Order ID to mark COMPLETED (0 to skip): ");
        int id = BaseSystem.readInt(sc);
        if (id == 0) return;
        Order o = orders.stream().filter(ord -> ord.getId() == id).findFirst().orElse(null);
        if (o != null) { o.setStatus("COMPLETED"); System.out.println("✅ Order marked COMPLETED!"); }
        else System.out.println("❌ Order not found.");
    }
}
