package user.controller;

import user.model.User;
import user.model.UserProfile;
import user.service.UserService;
import user.service.impl.UserServiceImpl;
import user.validation.UserValidationException;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
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
		// Simply show the signup page when a user visits /signup instead of the /signup.jsp
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Extract all form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String rawPassword = request.getParameter("password"); // Kept as a separate variable! will not be in the user class
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        
        try {
            // 2. Map form data into the User and UserProfile models
            User newUser = new User();
            newUser.setUsername(username != null ? username.trim() : "");
            newUser.setEmail(email != null ? email.trim() : "");
            
            UserProfile profile = new UserProfile(
                firstName, lastName, phoneNumber != null ? phoneNumber.trim() : "", 
                address, city, country
            );
            newUser.setProfile(profile);

            // 3. Pass models and raw password string separately to service layer
            boolean registerSuccess = userService.registerNewCustomer(newUser, rawPassword);

            if (registerSuccess) {
                // Success! Send them directly to the login page
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } else {
                request.setAttribute("signupError", "Database error: Could not complete registration.");
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
            }

        } catch (UserValidationException e) {
            // 4. Catch validation failures and return the specific message back to the JSP
            request.setAttribute("signupError", e.getMessage());
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        }
	}

}
