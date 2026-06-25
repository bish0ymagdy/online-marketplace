package item.model;

public class Item {
	


    // Instance variables
	private int id;
    private String name;
    private double price;
    private String description;
    private int userId; // The ID of the user who added this item

    // Constructors
    public Item() {}
    
    public Item(String name, double price, String description, int userId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.userId = userId;
    }

    public Item(int id, String name, double price, String description, int userId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.userId = userId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    // toString method for easy printing/debugging
    @Override
    public String toString() {
        return "Item [ID=" + id + ", Name=" + name + ", Price=$" + price + ", description" + description + "created by user_id" + userId + "]";
    }

}
