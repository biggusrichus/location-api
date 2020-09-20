package biggusrichus.locationapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import biggusrichus.locationapi.controller.exception.UserNotFoundException;
import biggusrichus.locationapi.entity.LocationHistory;
import biggusrichus.locationapi.entity.User;
import biggusrichus.locationapi.repository.LocationHistoryRepository;
import biggusrichus.locationapi.repository.UserRepository;

/**
 * A service for managing users and their associated location history.
 * 
 * @author richard.brooks
 *
 */
@Service
public class UserService {

	private final UserRepository userRepository;
	private final LocationHistoryRepository locationHistoryRepository;

	public UserService(final UserRepository userRepository, final LocationHistoryRepository locationHistoryRepository) {
		Validate.notNull(userRepository, "userRepository must not be null");
		Validate.notNull(locationHistoryRepository, "locationHistoryRepository must not be null");

		this.userRepository = userRepository;
		this.locationHistoryRepository = locationHistoryRepository;
	}

	/**
	 * Find all users.
	 * 
	 * @return list of all users
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * Find a user by id.
	 * 
	 * @param id the id of the user (must not be {@code null}).
	 * @return the {@link User} corresponding to the id.
	 * @throws UserNotFoundException if no user with the id exists.
	 */
	public User findById(final Long id) throws UserNotFoundException {
		Validate.notNull(id, "id must not be null");

		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	/**
	 * Find all users within an area specified by a pair of latitudes and
	 * longitudes.
	 * 
	 * @param minLat first latitude (must not be {@code null}).
	 * @param minLon first longitude (must not be {@code null}).
	 * @param maxLat second latitude (must not be {@code null}).
	 * @param maxLon second longitude (must not be {@code null}).
	 * @return a list of {@link User}s with latitude/longitudes within the specified
	 *         boundary.
	 */
	public List<User> findByGrid(final BigDecimal minLat, final BigDecimal minLon, final BigDecimal maxLat,
			final BigDecimal maxLon) {
		Validate.notNull(minLat, "minLat must not be null");
		Validate.notNull(minLon, "minLon must not be null");
		Validate.notNull(maxLat, "maxLat must not be null");
		Validate.notNull(maxLon, "maxLon must not be null");

		return userRepository.findByGrid(minLat, minLon, maxLat, maxLon);
	}

	/**
	 * Add a user.
	 * 
	 * @param newUser the user to add (must not be {@code null}).
	 * @return the {@link User}.
	 */
	public User addUser(final User newUser) {
		Validate.notNull(newUser, "newUser must not be null");

		User user = userRepository.save(newUser);
		locationHistoryRepository
				.save(new LocationHistory(user, LocalDateTime.now(), user.getLatitude(), user.getLongitude()));
		return user;
	}

	/**
	 * Replace an existing user.
	 * 
	 * @param id      the id of the user to replace
	 * @param newUser the user to replace with
	 * @return the {@link User} once replaced
	 * @throws UserNotFoundException if the user to replace does not exist
	 */
	public User replaceUser(final Long id, final User newUser) throws UserNotFoundException {
		Validate.notNull(newUser, "newUser must not be null");
		Validate.notNull(id, "id must not be null");

		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		// create a new location history if location has changed
		if (user.getLatitude() != newUser.getLatitude() || user.getLongitude() != newUser.getLongitude()) {
			locationHistoryRepository
					.save(new LocationHistory(user, LocalDateTime.now(), user.getLatitude(), user.getLongitude()));
		}

		user.setName(newUser.getName());
		user.setLatitude(newUser.getLatitude());
		user.setLongitude(newUser.getLongitude());

		return userRepository.save(user);
	}

	/**
	 * Update a user.
	 * 
	 * @param id      the id of the user to update.
	 * @param updates a Map of key/value {@link User} attributes
	 * @return the {@link User} once updated
	 * @throws UserNotFoundException
	 */
	public User updateUser(final Long id, final Map<String, String> updates) throws UserNotFoundException {
		Validate.notNull(updates, "updates must not be null");
		Validate.notNull(id, "id must not be null");

		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		boolean locationUpdated = false;

		for (Map.Entry<String, String> entry : updates.entrySet()) {
			switch (entry.getKey()) {
			case "name":
				user.setName((String) entry.getValue());
				break;
			case "latitude":
				BigDecimal latitude = new BigDecimal(entry.getValue());
				if (!latitude.equals(user.getLatitude())) {
					user.setLatitude(latitude);
					locationUpdated = true;
				}
				break;
			case "longitude":
				BigDecimal longitude = new BigDecimal(entry.getValue());
				if (!longitude.equals(user.getLongitude())) {
					user.setLongitude(longitude);
					locationUpdated = true;
				}
				break;
			default: // Ignore an unknown attribute
				break;
			}
		}

		if (locationUpdated) {
			locationHistoryRepository
					.save(new LocationHistory(user, LocalDateTime.now(), user.getLatitude(), user.getLongitude()));
		}

		return userRepository.save(user);
	}

	/**
	 * Delete a user
	 * 
	 * @param id the id of the user to delete.
	 */
	public void deleteUser(final Long id) {
		Validate.notNull(id, "id must not be null");

		locationHistoryRepository.deleteByUserId(id);
		userRepository.deleteById(id);
	}
}
