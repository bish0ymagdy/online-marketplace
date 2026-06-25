package user.validation;

import java.util.Set;

import user.model.User;

import DAOs.UserDAO;

public class UserValidator {
	
	private final UserDAO userDAO;

    // The validator needs access to the DAO to check for duplicates in the DB
    public UserValidator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public void validateRegistration(User user, String plainTextPassword ) throws UserValidationException {
        if (user == null) {
            throw new UserValidationException("User data cannot be entirely missing.");
        }
        
        // Run individual validation modules
        validateUsername(user.getUsername());
        validatePassword(plainTextPassword);
        validateEmail(user.getEmail());
        validatePhoneNumber(user.getProfile().getPhoneNumber());
    }
    
    private void validateUsername(String username) throws UserValidationException {
        if (username == null || username.isBlank()) {
            throw new UserValidationException("Username cannot be empty.");
        }

        if (username.length() < 3 || username.length() > 15) {
            throw new UserValidationException("Username must be 3–15 characters.");
        }

        if (!Character.isUpperCase(username.charAt(0))) {
            throw new UserValidationException("Username must start with an uppercase letter.");
        }

        if (userDAO.isUsernameExists(username)) {
            throw new UserValidationException("Username already exists.");
        }
    }
    
    private void validatePassword(String password) throws UserValidationException {
        // 1. Check if empty or null
        if (password == null || password.isBlank()) {
            throw new UserValidationException("Password cannot be empty");
        }

        // 2. Length check
        if (password.length() < 8) {
            throw new UserValidationException("Password must be at least 8 characters long");
        }

        // 3. Complexity Flags
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        // 4. Character scanning loop
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true; // Treats anything else (spaces, punctuation, symbols) as special
        }

        // 5. Enforce Complexity Rules with Exception Triggers
        if (!hasUpper) {
            throw new UserValidationException("Password must contain at least one uppercase letter");
        }

        if (!hasLower) {
            throw new UserValidationException("Password must contain at least one lowercase letter");
        }

        if (!hasDigit) {
            throw new UserValidationException("Password must contain at least one digit");
        }

        if (!hasSpecial) {
            throw new UserValidationException("Password must contain at least one special character");
        }
    }
    
    public void validatePhoneNumber(String phoneNumber) throws UserValidationException {
    	
    	final Set<String> EGYPT_PREFIXES = Set.of("010", "011", "012", "015");
        // 1. Null / blank check
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new UserValidationException("Phone number cannot be empty");
        }

        // 2. Numeric only validation
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new UserValidationException("Phone number must contain digits only");
            }
        }

        // 3. Length validation
        if (phoneNumber.length() != 11) {
            throw new UserValidationException("Phone number must be exactly 11 digits");
        }

        // 4. Real Database Uniqueness check
        if (userDAO.isPhoneNumberExists(phoneNumber)) {
            throw new UserValidationException("Phone number already registered");
        }

        // 5. Egyptian Mobile Prefix validation
        boolean validPrefix = EGYPT_PREFIXES.stream().anyMatch(phoneNumber::startsWith);
        if (!validPrefix) {
            throw new UserValidationException("Invalid Egyptian mobile number. Must start with 010, 011, 012, or 015");
        }
        
        // Everything passes!
    }
    
    private void validateEmail(String email) throws UserValidationException {
        // 1. Null / blank check
        if (email == null || email.isBlank()) {
            throw new UserValidationException("Email cannot be empty.");
        }
        
        // Clean the input string
        String cleanEmail = email.trim().toLowerCase();

        // 2. Format validation check
        if (!cleanEmail.contains("@") || !cleanEmail.contains(".")) {
            throw new UserValidationException("Please enter a valid email address.");
        }

        // 3. Real Database Uniqueness check
        if (userDAO.isEmailExists(cleanEmail)) {
            throw new UserValidationException("Email address is already registered.");
        }
    }

}
