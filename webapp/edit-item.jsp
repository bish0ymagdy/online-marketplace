<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="item.model.Item" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Market Item</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #121212;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .edit-card {
            background-color: #1e1e1e;
            padding: 2.5rem;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.5);
            width: 100%;
            max-width: 450px;
            border: 1px solid #2d2d2d;
        }

        h2 {
            color: #ffffff;
            margin-bottom: 1.5rem;
            text-align: center;
            font-size: 1.8rem;
        }

        .form-group {
            margin-bottom: 1.25rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #aaaaaa;
            font-size: 0.9rem;
            font-weight: 600;
        }

        input[type="text"],
        input[type="number"],
        textarea {
            width: 100%;
            padding: 0.75rem;
            background-color: #121212;
            border: 1px solid #333333;
            border-radius: 6px;
            font-size: 1rem;
            color: #ffffff;
            outline: none;
            transition: border-color 0.2s;
        }

        textarea {
            resize: vertical;
            height: 100px;
        }

        input:focus, textarea:focus {
            border-color: #3b82f6;
        }

        .btn-container {
            display: flex;
            gap: 1rem;
            margin-top: 1.5rem;
        }

        button[type="submit"] {
            flex: 2;
            padding: 0.75rem;
            background-color: #3b82f6;
            color: #ffffff;
            border: none;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        button[type="submit"]:hover { background-color: #2563eb; }

        .btn-cancel {
            flex: 1;
            padding: 0.75rem;
            background-color: #2d2d2d;
            color: #aaaaaa;
            text-align: center;
            text-decoration: none;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: bold;
            border: 1px solid #333333;
            transition: background-color 0.2s, color 0.2s;
        }

        .btn-cancel:hover {
            background-color: #333333;
            color: #ffffff;
        }
    </style>
</head>
<body>

    <%
        Item item = (Item) request.getAttribute("item");
        if (item == null) {
            response.sendRedirect(request.getContextPath() + "/items");
            return;
        }
    %>

    <div class="edit-card">
        <h2>Update Item Profile</h2>
        
        <form action="items" method="POST">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= item.getId() %>">

            <div class="form-group">
                <label for="name">Item Name</label>
                <input type="text" id="name" name="name" required value="<%= item.getName() %>">
            </div>

            <div class="form-group">
                <label for="price">Price (EGP)</label>
                <input type="number" id="price" name="price" step="0.01" required value="<%= item.getPrice() %>">
            </div>

            <div class="form-group">
                <label for="description">Product Description</label>
                <textarea id="description" name="description" required><%= item.getDescription() %></textarea>
            </div>

            <div class="btn-container">
                <a href="items" class="btn-cancel">Cancel</a>
                <button type="submit">Save Changes</button>
            </div>
        </form>
    </div>

</body>
</html>