public class User {
    private int id;
    private String username;
    private String password;
    private String role; // ADMIN / CUSTOMER
    private boolean active;
    private String fullName;

    public User(int id, String username, String password, String role, boolean active, String fullName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
        this.fullName = fullName;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public boolean isActive() { return active; }
    public String getFullName() { return fullName; }

    public void setActive(boolean active) { this.active = active; }
}
