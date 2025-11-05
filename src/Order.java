import java.util.List;

public class Order {
    private int id;
    private int userId;
    private List<Cart.CartItem> items;
    private double subtotal, tax, total;
    private String status;
    private String paymentMethod;

    public Order(int id, int userId, List<Cart.CartItem> items, double subtotal, double tax, double total,
                 String status, String paymentMethod) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String brief() {
        return "Order " + id + " | User " + userId + " | " + status + " | " + paymentMethod +
                " | Total " + BaseSystem.formatMoney(total);
    }

    public List<Cart.CartItem> getItems() { return items; }
}
