package user.service;

import user.model.User;
import user.model.UserProfile;
import user.validation.UserValidationException;

public interface UserService {
    
    /** Registers a user, hashes password, and builds profile */
    boolean registerNewCustomer(User user, String plainTextPassword) throws UserValidationException;
    
    /** Logs a user in by checking credentials */
    User authenticateUser(String email, String plainPassword);
    
    /** Updates profile attributes (Address, Phone, etc.) */
    boolean updateProfileDetails(UserProfile profile) throws UserValidationException;
    
    /** Checks if an email is already registered */
    boolean isEmailAvailable(String email);
    
    /** Checks if a username is already registered */
    boolean isUsernameAvailable(String username);
}
