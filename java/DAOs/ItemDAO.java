package DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import item.model.Item;

public class ItemDAO extends BaseDAO {
	
	public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        String query = "SELECT id, name, price, description, user_id FROM items";

        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("user_id")
                );
                itemList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }
	
	public Item getItemById(int itemId) {
		String query = "SELECT id, name, price, description, user_id FROM items WHERE id = ?";
		
		try (Connection conn = getConnection(); 
	         PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setInt(1, itemId);
	      
	        try (ResultSet rs = stmt.executeQuery()) {
	        	 if (rs.next()) {
	                 return new Item(
	                		 rs.getInt("id"),
	                         rs.getString("name"),
	                         rs.getDouble("price"),
	                         rs.getString("description"),
	                         rs.getInt("user_id")
	                 );
	        	 }
	        }
		}catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public boolean insertItem(Item item) {
        String query = "INSERT INTO items (name, price, description, user_id) VALUES (?, ?, ?, ?)";
        boolean rowInserted = false;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setString(3, item.getDescription());
            stmt.setInt(4, item.getUserId());
            
            // executeUpdate returns the number of rows affected
            rowInserted = stmt.executeUpdate() > 0; 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }
	
	public boolean updateItem(Item item) {
        String query = "UPDATE items SET name = ?, price = ?, description = ? WHERE id = ?";
        boolean rowUpdated = false;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setString(3, item.getDescription());
            stmt.setInt(4, item.getId());
            
            rowUpdated = stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }
	
	public boolean deleteItem(int itemId) {
        String query = "DELETE FROM items WHERE id = ?";
        boolean rowDeleted = false;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, itemId);
            rowDeleted = stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
	
}
