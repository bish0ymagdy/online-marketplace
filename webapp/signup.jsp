<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Online Market - Sign Up</title>
    <style>
        /* Reset and Base Styles */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        body {
            background-color: #000000; /* Pure pitch black */
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 40px 20px;
        }

        /* Carbon Signup Card */
        .signup-card {
            background-color: #111111; /* Ultra-dark carbon/charcoal gray */
            padding: 40px;
            border-radius: 8px;
            border: 1px solid #222222; /* Minimalistic dark border */
            box-shadow: 0 0 20px rgba(16, 185, 129, 0.1); /* Subtle emerald glow */
            width: 100%;
            max-width: 500px;
        }

        /* Crisp white main title */
        h2 {
            color: #ffffff;
            margin-bottom: 24px;
            text-align: center;
            font-size: 1.8rem;
            font-weight: bold;
            letter-spacing: -0.5px;
        }

        /* Section Headings with Neon Emerald border */
        h3 {
            color: #10b981; /* Electric Emerald Green */
            font-size: 1rem;
            margin-top: 24px;
            margin-bottom: 16px;
            padding-bottom: 4px;
            border-bottom: 1px solid #222222;
            text-transform: uppercase;
            letter-spacing: 0.8px;
            font-weight: 700;
        }

        /* Vivid Red Error Box */
        .error-message {
            background-color: rgba(220, 38, 38, 0.1);
            color: #ef4444;
            padding: 12px;
            border-radius: 4px;
            border: 1px solid rgba(220, 38, 38, 0.3);
            margin-bottom: 20px;
            font-size: 0.95rem;
            text-align: center;
            font-weight: bold;
        }

        .form-group {
            margin-bottom: 16px;
        }

        /* Muted slate labels */
        label {
            display: block;
            margin-bottom: 6px;
            color: #888888;
            font-size: 0.85rem;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            font-weight: 600;
        }

        /* Deep dark inputs with bright white text */
        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="tel"] {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #333333;
            border-radius: 4px;
            font-size: 1rem;
            color: #ffffff;
            background-color: #1a1a1a;
            outline: none;
            transition: border-color 0.2s, box-shadow 0.2s;
        }

        /* Gray placeholders to prevent visual clutter */
        input[type="text"]::placeholder,
        input[type="email"]::placeholder,
        input[type="password"]::placeholder,
        input[type="tel"]::placeholder {
            color: #555555;
        }

        /* Neon Emerald Glow on active input fields */
        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus,
        input[type="tel"]:focus {
            border-color: #10b981;
            box-shadow: 0 0 0 2px rgba(16, 185, 129, 0.15);
        }

        /* Neon Emerald Action Button */
        button[type="submit"] {
            width: 100%;
            padding: 14px;
            background-color: #10b981;
            color: #000000; /* Dark high contrast text on bright green background */
            border: none;
            border-radius: 4px;
            font-size: 1.1rem;
            font-weight: 700;
            cursor: pointer;
            margin-top: 24px;
            transition: background-color 0.2s;
        }

        button[type="submit"]:hover {
            background-color: #059669;
        }

        /* Darker, low-contrast footer linkage */
        .login-link {
            text-align: center;
            margin-top: 24px;
            font-size: 0.95rem;
            color: #555555;
        }

        .login-link a {
            color: #10b981;
            text-decoration: none;
            font-weight: bold;
        }

        .login-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="signup-card">
        <h2>Create Your Account</h2>
        
        ${not empty signupError ? '<div class="error-message">' : ''}
            ${signupError}
        ${not empty signupError ? '</div>' : ''}

        <form action="signup" method="POST">
            
            <h3>Account Credentials</h3>
            
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required value="${param.username}" placeholder="Choose a username">
            </div>

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" required value="${param.email}" placeholder="example@email.com">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required placeholder="Create a strong password">
            </div>

            <h3>Profile Details</h3>
            
            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" required value="${param.firstName}" placeholder="Enter your first name">
            </div>

            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" required value="${param.lastName}" placeholder="Enter your last name">
            </div>

            <div class="form-group">
                <label for="phoneNumber">Phone Number</label>
                <input type="tel" id="phoneNumber" name="phoneNumber" value="${param.phone}" placeholder="e.g., 01012345678">
            </div>
            
            <div class="form-group">
                <label for="phone">Address</label>
                <input type="text" id="address" name="address" value="${param.address}" placeholder="e.g., buliding no. Street">
            </div>

            <div class="form-group">
                <label for="city">City</label>
                <input type="text" id="city" name="city" value="${param.city}" placeholder="e.g., Cairo, Alexandria">
            </div>
            
            <div class="form-group">
                <label for="city">Country</label>
                <input type="text" id="country" name="country" value="${param.country}" placeholder="e.g., Egypt">
            </div>

            <div>
                <button type="submit">Register Account</button>
            </div>
        </form>
        
        <div class="login-link">
            <p>Already have an account? <a href="login.jsp">Login here</a></p>
        </div>
    </div>

</body>
</html>