package biggusrichus.locationapi.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import biggusrichus.locationapi.entity.User;

/**
 * A JPA repository for {@link User}s.
 * 
 * @author richard.brooks
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where (u.latitude between ?1 and ?3) and (u.longitude between ?2 and ?4)")
	public List<User> findByGrid(final BigDecimal minLat, final BigDecimal minLon, final BigDecimal maxLat, final BigDecimal maxLon);
}
