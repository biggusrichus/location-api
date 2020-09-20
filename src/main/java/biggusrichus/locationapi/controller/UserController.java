package biggusrichus.locationapi.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biggusrichus.locationapi.controller.exception.SearchParameterException;
import biggusrichus.locationapi.controller.exception.UserNotFoundException;
import biggusrichus.locationapi.entity.User;
import biggusrichus.locationapi.service.UserService;

/**
 * A REST controller for users and their locations.
 * 
 * @author richard.brooks
 *
 */
@RestController
public class UserController {

	/** The User Service */
	private final UserService service;

	/**
	 * Constructor.
	 * 
	 * @param service the {@link UserService}.
	 */
	public UserController(UserService service) {
		this.service = service;
	}

	/**
	 * Get a list of users. Can either be used with no parameters to get all users
	 * and their locations or with minimum and maximum latitudes and longitudes to
	 * return users located within the defined rectangle.
	 * 
	 * @param minLat the minimum latitude of a boundary to search within (may be
	 *               {@code null} if all other parameters are {@code null}).
	 * @param minLon the minimum longitude of a boundary to search within (may be
	 *               {@code null} if all other parameters are {@code null}).
	 * @param maxLat the maximum latitude of a boundary to search within (may be
	 *               {@code null} if all other parameters are {@code null}).
	 * @param maxLon the maximum longitude of a boundary to search within (may be
	 *               {@code null} if all other parameters are {@code null}).
	 * @return a list of all users or a list of users within the specified boudary.
	 * @throws SearchParameterException if any number of parameters other than 0 or
	 *                                  4 are supplied.
	 */
	@GetMapping("/users")
	public List<User> findAll(@RequestParam Optional<BigDecimal> minLat, @RequestParam Optional<BigDecimal> minLon,
			@RequestParam Optional<BigDecimal> maxLat, @RequestParam Optional<BigDecimal> maxLon)
			throws SearchParameterException {

		if (minLat.isEmpty() && minLon.isEmpty() && maxLat.isEmpty() && maxLon.isEmpty()) {
			return service.findAll();
		}

		if (minLat.isPresent() && minLon.isPresent() && maxLat.isPresent() && maxLon.isPresent()) {
			return service.findByGrid(minLat.get(), minLon.get(), maxLat.get(), maxLon.get());
		}

		throw new SearchParameterException("Either supply no parameters or two pairs of lat/longs");
	}

	/**
	 * Get a user.
	 * 
	 * @param id the user id.
	 * @return the {@link User} matching the id.
	 * @throws UserNotFoundException if no {@link User} matching the requested id is
	 *                               found.
	 */
	@GetMapping("/users/{id}")
	public User findById(@PathVariable Long id) throws UserNotFoundException {
		return service.findById(id);
	}

	/**
	 * Add a user.
	 * 
	 * @param newUser the user to add. Must be a valid {@link User}.
	 * @return the user once added.
	 */
	@PostMapping("/users")
	public User addUser(@Valid @RequestBody User newUser) {
		return service.addUser(newUser);
	}

	/**
	 * Replace a user.
	 * 
	 * @param newUser the user to replace with.
	 * @param id      the id of the user to replace.
	 * @return the {@link User} once replaced.
	 * @throws UserNotFoundException if no {@link User} matching the requested id is
	 *                               found.
	 */
	@PutMapping("/users/{id}")
	public User replaceUser(@Valid @RequestBody User newUser, @PathVariable Long id) throws UserNotFoundException {
		return service.replaceUser(id, newUser);
	}

	/**
	 * Update a user.
	 * 
	 * @param updates a {@link Map} of key value pairs containing attribute names
	 *                and values. Only attributes to be updated need be supplied;
	 *                attributes not included will not be updated (and not set to
	 *                null).
	 * @param id the id of the user to update.
	 * @return the {@link User} once updated.
	 * @throws UserNotFoundException if no {@link User} matching the requested id is
	 *                               found.
	 */
	@PatchMapping("/users/{id}")
	public User updateUser(@RequestBody Map<String, String> updates, @PathVariable Long id)
			throws UserNotFoundException {
		return service.updateUser(id, updates);
	}

	/**
	 * Delete a user.
	 * 
	 * @param id the id of the user to delete.
	 */
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Long id) {
		service.deleteUser(id);
	}
}
