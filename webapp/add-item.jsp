<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Market Item</title>
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

        .form-card {
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
            border-color: #10b981; /* Emerald Green focus state */
        }

        .btn-container {
            display: flex;
            gap: 1rem;
            margin-top: 1.5rem;
        }

        button[type="submit"] {
            flex: 2;
            padding: 0.75rem;
            background-color: #10b981; /* Emerald Green */
            color: #ffffff;
            border: none;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        button[type="submit"]:hover { background-color: #059669; }

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
        // Security Gate: Ensure the client has an active authenticated user session profile
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
    %>

    <div class="form-card">
        <h2>List New Product</h2>
        
        <form action="items" method="POST">
            
            <input type="hidden" name="action" value="add">

            <div class="form-group">
                <label for="name">Product Title</label>
                <input type="text" id="name" name="name" placeholder="e.g., Samsung Galaxy A56" required autocomplete="off">
            </div>

            <div class="form-group">
                <label for="price">Price (EGP)</label>
                <input type="number" id="price" name="price" step="1" min="0.0" placeholder="0.0" required>
            </div>

            <div class="form-group">
                <label for="description">Item Details / Specification</label>
                <textarea id="description" name="description" placeholder="Provide description notes regarding your item profile details..." required></textarea>
            </div>

            <div class="btn-container">
                <a href="items" class="btn-cancel">Cancel</a>
                <button type="submit">Publish Item</button>
            </div>
        </form>
    </div>

</body>
</html>