package user.validation;

public class UserValidationException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	// Pass the exact validation failure message up to the caller
    public UserValidationException(String message) {
        super(message);
    }
}
