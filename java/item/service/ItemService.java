package item.service;

import java.util.List;
import item.model.Item;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(int itemId);
    boolean addItem(Item item);
    boolean updateItem(Item item, int currentUserId) throws IllegalAccessException;
    boolean deleteItem(int itemId, int currentUserId) throws IllegalAccessException;
}
