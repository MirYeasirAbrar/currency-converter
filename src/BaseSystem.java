import java.util.List;
import java.util.Scanner;

public class BaseSystem {

    // ---------- UI & Utility ----------
    public static void printHeader(String title) {
        System.out.println("\n\t\t\t" + title);
    }

    public static int readInt(Scanner sc) {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("⚠️ Enter a valid number: ");
            }
        }
    }

    public static double readDouble(Scanner sc) {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.print("⚠️ Enter a valid number: ");
            }
        }
    }

    public static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("⚠️ Cannot be empty.");
        }
    }

    public static String formatMoney(double val) {
        return String.format("$%.2f", val);
    }

    // ---------- Auth ----------
    public static User login(Scanner sc, List<User> users) {
        printHeader("🔑 Login");
        String username = readNonEmpty(sc, "Username: ");
        String password = readNonEmpty(sc, "Password: ");

        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                if (!u.isActive()) {
                    System.out.println("❌ Account disabled.");
                    return null;
                }
                System.out.println("✅ Welcome, " + u.getFullName() + " (" + u.getRole() + ")");
                return u;
            }
        }
        System.out.println("❌ Invalid credentials.");
        return null;
    }

    public static User signUp(Scanner sc, List<User> users, int[] userIdCounter) {
        printHeader("📝 Sign Up (Customer)");
        String username = readNonEmpty(sc, "Choose username: ");
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("❌ Username already taken.");
                return null;
            }
        }
        String password = readNonEmpty(sc, "Choose password: ");
        String fullName = readNonEmpty(sc, "Full name: ");

        User u = new User(userIdCounter[0]++, username, password, "CUSTOMER", true, fullName);
        users.add(u);
        System.out.println("✅ Account created! Logged in as " + username);
        return u;
    }
}
