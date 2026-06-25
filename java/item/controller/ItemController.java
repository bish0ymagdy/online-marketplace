package item.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.model.User;
import item.model.Item;
import item.service.ItemService;
import item.service.impl.ItemServiceImpl;


@WebServlet("/items")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	private ItemService itemService;

    public void init() throws ServletException {
        this.itemService = new ItemServiceImpl();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Security Guard: Make sure the visitor is an authenticated user
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
        	editPage(request, response);
            return;
        }
		
        try {
        	List<Item> itemList;
            String filter = request.getParameter("filter");
            String searchQuery = request.getParameter("search");

            //Filter to show only items owned by the current logged-in user
            if ("mine".equals(filter)) {
                itemList = itemService.getAllItems().stream()
                                .filter(item -> item.getUserId() == currentUser.getId())
                                .toList();
                request.setAttribute("currentFilter", "mine");
            } else {
                itemList = itemService.getAllItems();
                request.setAttribute("currentFilter", "all");
            }
            //Filter matching text inside Name or Description fields
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                String cleanSearch = searchQuery.toLowerCase().trim();
                itemList = itemList.stream()
                                .filter(item -> item.getName().toLowerCase().contains(cleanSearch) || 
                                                item.getDescription().toLowerCase().contains(cleanSearch))
                                .toList();
                request.setAttribute("lastSearch", searchQuery);
            }
            // Send the item catalog list forward to the UI compilation layer
            request.setAttribute("items", itemList);
            request.getRequestDispatcher("/items.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Failed to retrieve marketplace catalog items.");
            request.getRequestDispatcher("/items.jsp").forward(request, response);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Security Guard check
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
		
		String action = request.getParameter("action");
		if (Objects.isNull(action)) {
		    request.setAttribute("loginError", "Invalid or missing form action.");
		    // 1. Send them back to the view page with the error visible
		    doGet(request, response); 
		    // 2. STOP execution right here so the crash below never happens
		    return; 
		}
		
		try {
            if ("add".equals(action)) {
            	addItem(request, response);
            } else if ("delete".equals(action)) {
            	deleteItem(request, response);
            }else if ("update".equals(action)) {
                updateItem(request, response);
            }
        } catch (Exception e) {
            // Capture any Unchecked exceptions thrown from service validations (like SecurityException)
            request.setAttribute("loginError", e.getMessage());
            // Bounce them cleanly back to catalog dashboard with notification banner
            doGet(request, response);
        }
	}
	
	private void editPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	    	User currentUser = (User) request.getSession().getAttribute("currentUser");
	        int itemId = Integer.parseInt(request.getParameter("id"));
	        Item item = itemService.getItemById(itemId);

	        if (item == null) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found.");
	            return;
	        }

	        // BACKEND SECURITY CHECK: Prevent users from manually typing the URL to edit other profiles
	        if (item.getUserId() != currentUser.getId()) {
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You do not own this item.");
	            return;
	        }

	        request.setAttribute("item", item);
	        request.getRequestDispatcher("/edit-item.jsp").forward(request, response);
	    } catch (Exception e) {
	        response.sendRedirect(request.getContextPath() + "/items");
	    }
	}

	private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, IOException {
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		int id = Integer.parseInt(request.getParameter("id"));
	    String name = request.getParameter("name");
	    double price = Double.parseDouble(request.getParameter("price"));
	    String description = request.getParameter("description");

	    // Construct the item object with modified fields
	    Item updatedItem = new Item(id, name, price, description, currentUser.getId());

	    // Our service layer checks item ownership before executing the update query
	    itemService.updateItem(updatedItem, currentUser.getId());

	    // Redirect cleanly back to the catalog board view
	    response.sendRedirect(request.getContextPath() + "/items");
		
	}

	private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException {
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		int itemId = Integer.parseInt(request.getParameter("id"));
        itemService.deleteItem(itemId, currentUser.getId());
        response.sendRedirect(request.getContextPath() + "/items");
		
	}

	private void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");

        // Construct new item model instance with the logged-in user's identity attached
        Item newItem = new Item(name, price, description, currentUser.getId());

        // Your service layer handles business verification rules internally 
        itemService.addItem(newItem);
        
        // Post-Redirect-Get strategy pattern to prevent double-submitting data on browser refresh
        response.sendRedirect(request.getContextPath() + "/items");
		
	}

}
