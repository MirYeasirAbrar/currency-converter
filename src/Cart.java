import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(MenuItem item, int qty) {
        for (CartItem c : items) {
            if (c.getItem().getId() == item.getId()) {
                c.setQuantity(c.getQuantity() + qty);
                return;
            }
        }
        items.add(new CartItem(item, qty));
    }

    public void updateQuantity(int index, int qty) {
        if (index >= 0 && index < items.size()) {
            if (qty <= 0) items.remove(index);
            else items.get(index).setQuantity(qty);
        }
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) items.remove(index);
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(i -> i.getItem().getPrice() * i.getQuantity()).sum();
    }

    public List<CartItem> getItems() { return items; }

    public boolean isEmpty() { return items.isEmpty(); }

    public static class CartItem {
        private MenuItem item;
        private int quantity;

        public CartItem(MenuItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public MenuItem getItem() { return item; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
