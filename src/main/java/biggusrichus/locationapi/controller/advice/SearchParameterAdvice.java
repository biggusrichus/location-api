package biggusrichus.locationapi.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import biggusrichus.locationapi.controller.exception.SearchParameterException;

/**
 * Controller advice for handling a {@link SearchParameterException}.
 * 
 * @author richard.brooks
 *
 */
@ControllerAdvice
public class SearchParameterAdvice {

	@ResponseBody
	@ExceptionHandler(SearchParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String userNotFoundHandler(SearchParameterException ex) {
		return ex.getMessage();
	}
}
