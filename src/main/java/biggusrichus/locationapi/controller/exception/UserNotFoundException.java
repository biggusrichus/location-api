package biggusrichus.locationapi.controller.exception;

/**
 * A checked exception thrown when a user is not found.
 * 
 * @author richard.brooks
 *
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 7707469758534332596L;

	public UserNotFoundException(Long id) {
		super("Could not find user " + id);
	}
}
