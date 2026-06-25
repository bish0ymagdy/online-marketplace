<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Online Market - Login</title>
    <style>
        /* Reset and Base Styles */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #000000; /* Pure pitch black */
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        /* Card Container */
        .login-card {
            background-color: #111111; /* Ultra-dark carbon/charcoal gray */
            padding: 2.5rem;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(16, 185, 129, 0.1); /* Subtle emerald glow */
            width: 100%;
            max-width: 400px;
            border: 1px solid #222222; /* Minimalistic dark border */
        }

        h2 {
            color: #ffffff;
            margin-bottom: 1.5rem;
            text-align: center;
            font-size: 1.8rem;
            font-weight: 700;
            letter-spacing: -0.5px;
        }

        /* Error Message Styling */
        .error-message {
            background-color: rgba(220, 38, 38, 0.1);
            color: #ef4444; /* Vivid Red text */
            padding: 0.75rem;
            border-radius: 4px;
            border: 1px solid rgba(220, 38, 38, 0.3);
            margin-bottom: 1.25rem;
            font-size: 0.9rem;
            text-align: center;
        }

        /* Form Controls */
        .form-group {
            margin-bottom: 1.25rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #888888; /* Muted slate gray */
            font-size: 0.85rem;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            font-weight: 600;
        }

        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 0.75rem;
            background-color: #1a1a1a; /* Dark gray inputs */
            border: 1px solid #333333;
            border-radius: 4px;
            color: #ffffff;
            font-size: 1rem;
            transition: border-color 0.2s, box-shadow 0.2s;
            outline: none;
        }

        input[type="email"]::placeholder,
        input[type="password"]::placeholder {
            color: #555555;
        }

        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #10b981; /* Electric Emerald Green focus line */
            box-shadow: 0 0 0 2px rgba(16, 185, 129, 0.15);
        }

        /* Checkbox Utilities */
        .checkbox-group {
            display: flex;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        .checkbox-group input {
            margin-right: 0.5rem;
            cursor: pointer;
            accent-color: #10b981; /* Changes default checkbox to neon emerald */
        }

        .checkbox-group label {
            margin-bottom: 0;
            font-weight: normal;
            color: #888888;
            cursor: pointer;
            font-size: 0.9rem;
        }

        /* Button */
        button[type="submit"] {
            width: 100%;
            padding: 0.75rem;
            background-color: #10b981; /* Neon Emerald */
            color: #000000; /* High contrast black text on green button */
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: 700;
            cursor: pointer;
            transition: background-color 0.2s, opacity 0.2s;
        }

        button[type="submit"]:hover {
            background-color: #059669; /* Slightly deeper green on hover */
        }

        /* Signup Link Footer */
        .signup-link {
            text-align: center;
            margin-top: 1.5rem;
            font-size: 0.9rem;
            color: #555555;
        }

        .signup-link a {
            color: #10b981;
            text-decoration: none;
            font-weight: bold;
        }

        .signup-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="login-card">
        <h2>Market Login</h2>
        
        ${not empty loginError ? '<div class="error-message">' : ''}
            ${loginError}
        ${not empty loginError ? '</div>' : ''}

        <form action="login" method="POST">
            
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" required value="${param.email}" placeholder="Enter your email">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required placeholder="Enter your password">
            </div>

<%--            <div class="checkbox-group">
                <input type="checkbox" id="rememberMe" name="rememberMe">
                <label for="rememberMe">Remember me</label>
            </div> --%> 

            <div>
                <button type="submit">Sign In</button>
            </div>
        </form>
        
        <div class="signup-link">
            <p>Don't have an account? <a href="signup.jsp">Sign Up</a></p>
        </div>
    </div>

</body>
</html>