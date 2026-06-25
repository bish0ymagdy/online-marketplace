package user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.model.User;
import user.service.UserService;
import user.service.impl.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserService userService;


    public void init() throws ServletException {
        // Instantiate the service implementation
        this.userService = new UserServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
	    if ("logout".equals(action)) {
	        // 1. Grab the current session if it exists, and invalidate (destroy) it
	        if (request.getSession(false) != null) {
	            request.getSession().invalidate(); 
	        }
	        // 2. Clear out authentication headers and bounce them to the login form view
	        response.sendRedirect(request.getContextPath() + "/login");
	        return; // Stop code execution immediately!
	    }
		
		// Simply show the login page when a user visits /login instead of the /login.jsp
        request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Extract parameters from the form inputs
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            // Call your service layer to validate the credentials
            // (Adjust method name based on your exact UserServiceImpl implementation)
            User user = userService.authenticateUser(email, password); 

            if (user != null) {
                // Login Success: Create an HTTP Session to keep the user logged in
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", user);
                response.sendRedirect(request.getContextPath() + "/items");
            } else {
                // If authentication fails cleanly but without an exception
                request.setAttribute("loginError", "Invalid username or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // Login Failure (e.g., validation errors or database issues)
            request.setAttribute("loginError", e.getMessage()); 
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        
	}

}
