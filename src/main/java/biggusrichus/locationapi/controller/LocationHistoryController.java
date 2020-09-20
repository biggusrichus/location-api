package biggusrichus.locationapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biggusrichus.locationapi.entity.LocationHistory;
import biggusrichus.locationapi.repository.LocationHistoryRepository;

/**
 * A REST controller for location history.
 * 
 * @author richard.brooks
 *
 */
@RestController
public class LocationHistoryController {

	/** The Location History Repository */
	private final LocationHistoryRepository repository;

	/**
	 * Constructor.
	 * 
	 * @param repository the {@link LocationHistoryRepository}.
	 */
	public LocationHistoryController(LocationHistoryRepository repository) {
		this.repository = repository;
	}

	/**
	 * Get a list of location history entries for a user.
	 * 
	 * @param userId a user id.
	 * @return a list of {@link LocationHistory} for the requested user.
	 */
	@GetMapping("/locationHistory")
	public List<LocationHistory> findByUserId(@RequestParam Long userId) {
		return repository.findByUserId(userId);
	}
}
