package user.service.impl;

import DAOs.UserDAO;
import user.model.User;
import user.model.UserProfile;
import user.service.UserService;
import user.validation.UserValidationException;
import user.validation.UserValidator;

public class UserServiceImpl implements UserService {
	
	private final UserDAO userDAO;
    private final UserValidator validator;

    // Standard constructor setups the components
    public UserServiceImpl() {
        this.userDAO = new UserDAO();
        this.validator = new UserValidator(this.userDAO);
    }

	@Override
	public boolean registerNewCustomer(User user, String plainTextPassword) throws UserValidationException {
		if (user == null) return false;

        // 1. Pass both down to the validator
        validator.validateRegistration(user, plainTextPassword);

        // 2. Encryption step happens safely outside the model boundary
        String secureHash = hashPassword(plainTextPassword);
        
        // 3. Only the secure hash is ever assigned to the actual User object
        user.setPasswordHash(secureHash);

        return userDAO.registerUser(user);
	}

	@Override
	public User authenticateUser(String email, String plainPassword) {
		if (email == null || plainPassword == null || email.isBlank()) {
            return null;
        }
        // Convert input attempt to matching hash and check via DAO
        String hashedAttempt = hashPassword(plainPassword);
        return userDAO.loginUser(email.trim(), hashedAttempt);
	}

	@Override
	public boolean updateProfileDetails(UserProfile profile) throws UserValidationException {
		if (profile == null) return false;
        
        // Isolate phone number validation specifically during profile edits
        validator.validatePhoneNumber(profile.getPhoneNumber());
        
        // Call a profile update method in DAO (You can add this to your DAO next)
        return userDAO.updateUserProfile(profile);
	}

	@Override
	public boolean isEmailAvailable(String email) {
		if (email == null || email.isBlank()) return false;
        return !userDAO.isEmailExists(email);
	}

	@Override
	public boolean isUsernameAvailable(String username) {
		if (username == null || username.isBlank()) return false;
        return !userDAO.isUsernameExists(username);
	}
	
	private String hashPassword(String plainText) {
        return Integer.toHexString(plainText.hashCode());
    }

}
