package item.service.impl;

import java.util.List;

import DAOs.ItemDAO;
import item.model.Item;
import item.service.ItemService;

public class ItemServiceImpl implements ItemService {
	
	private final ItemDAO itemDAO = new ItemDAO();

	@Override
	public List<Item> getAllItems() {
		return itemDAO.getAllItems();
	}

	@Override
	public Item getItemById(int itemId) {
		return itemDAO.getItemById(itemId);	
	}

	@Override
	public boolean addItem(Item item) {
		if (item.getPrice() < 0) {
            throw new IllegalArgumentException("Item price cannot be negative.");
        }
        return itemDAO.insertItem(item);
	}

	@Override
	public boolean updateItem(Item item, int currentUserId) throws IllegalAccessException {
		Item existingItem = itemDAO.getItemById(item.getId());
        
        if (existingItem == null) {
            throw new IllegalArgumentException("Item not found with ID: " + item.getId());
        }
        
        // SECURITY GATE: Throw a proper exception instead of printing to console
        if (existingItem.getUserId() != currentUserId) {
            throw new IllegalAccessException("Unauthorized action! You do not own this item.");
        }
        
        return itemDAO.updateItem(item);
	}

	@Override
	public boolean deleteItem(int itemId, int currentUserId) throws IllegalAccessException {
Item existingItem = itemDAO.getItemById(itemId);
        
        if (existingItem == null) {
            throw new IllegalArgumentException("Item not found with ID: " + itemId);
        }
        
        // SECURITY GATE: Block the action immediately by breaking the execution flow
        if (existingItem.getUserId() != currentUserId) {
            throw new IllegalAccessException("Unauthorized action! You do not own this item.");
        }
        
        return itemDAO.deleteItem(itemId);
	}
	

}
