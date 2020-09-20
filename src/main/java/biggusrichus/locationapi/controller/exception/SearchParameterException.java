package biggusrichus.locationapi.controller.exception;

/**
 * A checked exception thrown when search parameters are not as expected.
 * 
 * @author richard.brooks
 *
 */
public class SearchParameterException extends Exception {

	private static final long serialVersionUID = -4631774430000577418L;

	public SearchParameterException(String message) {
		super(message);
	}

}
