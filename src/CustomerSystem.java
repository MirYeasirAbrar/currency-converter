import java.util.List;
import java.util.Scanner;

public class CustomerSystem {
    private User customer;
    private List<MenuItem> menuItems;
    private List<Order> orders;
    private int[] orderIdCounter;
    private Cart cart;

    public CustomerSystem(User customer, List<MenuItem> menuItems, List<Order> orders, int[] orderIdCounter) {
        this.customer = customer;
        this.menuItems = menuItems;
        this.orders = orders;
        this.orderIdCounter = orderIdCounter;
        this.cart = new Cart();
    }

    public void showCustomerMenu(Scanner sc) {
        while (true) {
            BaseSystem.printHeader("🛒 Customer Menu - " + customer.getFullName());
            System.out.println("[1] View Menu");
            System.out.println("[2] View Cart");
            System.out.println("[3] Order History");
            System.out.println("[4] Logout");
            System.out.print("Choose: ");
            int choice = BaseSystem.readInt(sc);

            switch (choice) {
                case 1 -> viewMenu(sc);
                case 2 -> viewCart(sc);
                case 3 -> orderHistory(sc);
                case 4 -> { return; }
                default -> System.out.println("⚠️ Invalid choice!");
            }
        }
    }

    private void viewMenu(Scanner sc) {
        BaseSystem.printHeader("📋 Menu");
        for (MenuItem m : menuItems) {
            if (m.isAvailable()) System.out.println(m);
        }
        System.out.print("Enter Menu ID to add to cart (0 to back): ");
        int id = BaseSystem.readInt(sc);
        if (id == 0) return;
        MenuItem item = menuItems.stream().filter(m -> m.getId() == id && m.isAvailable()).findFirst().orElse(null);
        if (item == null) { System.out.println("❌ Item not found."); return; }
        System.out.print("Quantity: ");
        int qty = BaseSystem.readInt(sc);
        if (qty <= 0) { System.out.println("⚠️ Invalid quantity."); return; }
        cart.addItem(item, qty);
        System.out.println("✅ Added to cart!");
    }

    private void viewCart(Scanner sc) {
        BaseSystem.printHeader("🛒 Your Cart");
        if (cart.isEmpty()) { System.out.println("(empty)"); return; }

        int idx = 1;
        for (Cart.CartItem ci : cart.getItems()) {
            System.out.println(idx + ". " + ci.getItem().getName() + " x" + ci.getQuantity() +
                    " = " + BaseSystem.formatMoney(ci.getItem().getPrice() * ci.getQuantity()));
            idx++;
        }
        System.out.println("Subtotal: " + BaseSystem.formatMoney(cart.getSubtotal()));
        System.out.println("[1] Update Quantity\n  [2] Remove Item\n  [3] Checkout\n  [4] Back");
        System.out.print("Choose: ");
        int choice = BaseSystem.readInt(sc);

        switch (choice) {
            case 1 -> {
                System.out.print("Item number: ");
                int i = BaseSystem.readInt(sc) - 1;
                System.out.print("New quantity: ");
                int qty = BaseSystem.readInt(sc);
                cart.updateQuantity(i, qty);
                System.out.println("✅ Updated!");
            }
            case 2 -> {
                System.out.print("Item number to remove: ");
                int i = BaseSystem.readInt(sc) - 1;
                cart.removeItem(i);
                System.out.println("❌ Removed!");
            }
            case 3 -> checkout(sc);
        }
    }

    private void checkout(Scanner sc) {
        if (cart.isEmpty()) { System.out.println("⚠️ Cart empty."); return; }
        double subtotal = cart.getSubtotal();
        double tax = subtotal * 0.05;
        double total = subtotal + tax;

        System.out.println("Subtotal: " + BaseSystem.formatMoney(subtotal));
        System.out.println("Tax (5%): " + BaseSystem.formatMoney(tax));
        System.out.println("Total: " + BaseSystem.formatMoney(total));

        System.out.print("Payment method (Cash/Card): ");
        String method = sc.nextLine().trim();
        orders.add(new Order(orderIdCounter[0]++, customer.getId(), cart.getItems(), subtotal, tax, total, "PENDING", method));
        cart = new Cart();
        System.out.println("✅ Order placed! Thank you.");
    }

    private void orderHistory(Scanner sc) {
        BaseSystem.printHeader("📜 Order History");
        boolean found = false;
        for (Order o : orders) {
            if (o.getUserId() == customer.getId()) {
                System.out.println(o.brief());
                found = true;
            }
        }
        if (!found) System.out.println("(none)");
    }
}
