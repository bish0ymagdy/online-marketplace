<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="item.model.Item" %>
<%@ page import="user.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Marketplace Catalog Dashboard</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', sans-serif; }
        body { background-color: #121212; color: #e0e0e0; padding: 2rem; min-height: 100vh; }
        .container { max-width: 1100px; margin: 0 auto; }
        
        .header-panel { border-bottom: 1px solid #2d2d2d; padding-bottom: 1.5rem; margin-bottom: 2rem; }
        h1 { color: #ffffff; font-size: 2rem; }
        
        .btn-add { background-color: #10b981; color: #ffffff; padding: 0.75rem 1.5rem; border-radius: 6px; text-decoration: none; font-weight: bold; transition: background 0.2s; }
        .btn-add:hover { background-color: #059669; }
        
        .btn-logout { color: #ef4444; text-decoration: none; font-weight: bold; font-size: 0.95rem; border: 1px solid #ef4444; padding: 0.5rem 1rem; border-radius: 6px; transition: all 0.2s; }
        .btn-logout:hover { background-color: rgba(239, 68, 68, 0.1); }
        
        .toolbar { display: flex; justify-content: space-between; align-items: center; background-color: #1e1e1e; padding: 1rem; border-radius: 8px; border: 1px solid #2d2d2d; gap: 1rem; flex-wrap: wrap; margin-top: 1.5rem; }
        .search-form { display: flex; gap: 0.5rem; flex-grow: 1; max-width: 500px; }
        .search-input { flex-grow: 1; padding: 0.6rem; background-color: #121212; border: 1px solid #333; border-radius: 4px; color: white; outline: none; }
        .btn-search { background-color: #3b82f6; color: white; border: none; padding: 0.6rem 1.2rem; border-radius: 4px; cursor: pointer; font-weight: bold; }
        .btn-clear { background-color: #2d2d2d; color: #aaa; padding: 0.6rem 1rem; border-radius: 4px; text-decoration: none; border: 1px solid #333; font-size: 0.9rem; }
        
        .filter-group { display: flex; gap: 0.5rem; }
        .filter-btn { padding: 0.6rem 1.2rem; border-radius: 4px; text-decoration: none; font-weight: bold; font-size: 0.9rem; }
        .filter-active-all { background-color: #3b82f6; color: white; }
        .filter-active-mine { background-color: #f59e0b; color: #121212; }
        .filter-inactive { background-color: #2d2d2d; color: #aaa; border: 1px solid #333; }
        
        .alert-banner { background-color: rgba(239, 68, 68, 0.15); border: 1px solid #ef4444; color: #f87171; padding: 1rem; border-radius: 6px; margin-bottom: 1.5rem; }
        .catalog-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 1.5rem; }
        .item-card { background-color: #1e1e1e; border: 1px solid #2d2d2d; border-radius: 8px; padding: 1.5rem; display: flex; flex-direction: column; justify-content: space-between; box-shadow: 0 4px 12px rgba(0,0,0,0.3); transition: transform 0.2s; }
        .item-card:hover { transform: translateY(-4px); }
        .item-title { color: #ffffff; font-size: 1.3rem; margin-bottom: 0.5rem; }
        .item-price { color: #3b82f6; font-size: 1.15rem; font-weight: bold; margin-bottom: 0.75rem; }
        .item-desc { color: #aaaaaa; font-size: 0.95rem; line-height: 1.4; margin-bottom: 1.5rem; flex-grow: 1; }
        
        .card-actions { display: flex; gap: 0.75rem; border-top: 1px solid #2d2d2d; padding-top: 1rem; }
        .btn-edit { flex: 1; background-color: #f59e0b; color: #121212; text-align: center; padding: 0.5rem; border-radius: 4px; text-decoration: none; font-weight: bold; font-size: 0.9rem; }
        .form-delete { flex: 1; display: flex; }
        .btn-delete { width: 100%; background-color: #ef4444; color: #ffffff; border: none; padding: 0.5rem; border-radius: 4px; font-weight: bold; font-size: 0.9rem; cursor: pointer; }
        .empty-state { grid-column: 1 / -1; text-align: center; padding: 4rem 2rem; color: #666666; font-size: 1.2rem; }
    </style>
</head>
<body>

    <%
        User currentUser = (User) session.getAttribute("currentUser");
        List<Item> items = (List<Item>) request.getAttribute("items");
        String loginError = (String) request.getAttribute("loginError");
        
        String currentFilter = (String) request.getAttribute("currentFilter");
        String lastSearch = (String) request.getAttribute("lastSearch");
        if (lastSearch == null) lastSearch = "";
        
        int currentUserId = (currentUser != null) ? currentUser.getId() : 0;
    %>

    <div class="container">
        
        <header class="header-panel" style="display: flex; flex-direction: column; gap: 1.5rem; align-items: stretch;">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <div>
                    <h1>Welcome Back!</h1>
                    <p style="color: #10b981; font-size: 0.95rem; margin-top: 0.25rem;"> <strong><%= currentUser.getUsername() %></strong></p>
                </div>
                
                <div style="display: flex; gap: 1rem; align-items: center;">
                    <a href="add-item.jsp" class="btn-add">+ Add New Product</a>
                    <a href="login?action=logout" class="btn-logout">Logout</a>
                </div>
            </div>

            <div class="toolbar">
                <form action="items" method="GET" class="search-form">
                    <% if("mine".equals(currentFilter)) { %>
                        <input type="hidden" name="filter" value="mine">
                    <% } %>
                    <input type="text" name="search" class="search-input" placeholder="Search product items..." value="<%= lastSearch %>">
                    <button type="submit" class="btn-search">Search</button>
                    <% if(!lastSearch.isEmpty()) { %>
                        <a href="items?filter=<%= (currentFilter != null) ? currentFilter : "all" %>" class="btn-clear">Clear</a>
                    <% } %>
                </form>

                <div class="filter-group">
                    <a href="items?filter=all<%= !lastSearch.isEmpty() ? "&search=" + lastSearch : "" %>" 
                       class="filter-btn <%= !"mine".equals(currentFilter) ? "filter-active-all" : "filter-inactive" %>">
                       All Items
                    </a>
                    <a href="items?filter=mine<%= !lastSearch.isEmpty() ? "&search=" + lastSearch : "" %>" 
                       class="filter-btn <%= "mine".equals(currentFilter) ? "filter-active-mine" : "filter-inactive" %>">
                       My Uploads Only
                    </a>
                </div>
            </div>
        </header>

        <% if (loginError != null && !loginError.isEmpty()) { %>
            <div class="alert-banner"><%= loginError %></div>
        <% } %>

        <main class="catalog-grid">
            <% 
                if (items != null && !items.isEmpty()) { 
                    for (Item item : items) {
            %>
                        <div class="item-card">
                            <div>
                                <h2 class="item-title"><%= item.getName() %></h2>
                                <div class="item-price"><%= item.getPrice() %> EGP</div>
                                <p class="item-desc"><%= item.getDescription() %></p>
                            </div>

                            <% if (item.getUserId() == currentUserId) { %>
                                <div class="card-actions">
                                    <a href="items?action=edit&id=<%= item.getId() %>" class="btn-edit">Update</a>
                                    <form action="items" method="POST" class="form-delete" onsubmit="return confirm('Are you sure you want to delete this item?');">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="<%= item.getId() %>">
                                        <button type="submit" class="btn-delete">Delete</button>
                                    </form>
                                </div>
                            <% } %>
                        </div>
            <% 
                    } 
                } else { 
            %>
                <div class="empty-state">
                    No matches found in the catalog directory. Try adjusting your filter choices!
                </div>
            <% } %>
        </main>
    </div>

</body>
</html>