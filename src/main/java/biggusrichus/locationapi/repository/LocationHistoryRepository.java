package biggusrichus.locationapi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import biggusrichus.locationapi.entity.LocationHistory;

/**
 * A JPA repository for {@link LocationHistory}s.
 * 
 * @author richard.brooks
 *
 */
public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long>  {

	@Query("select l from LocationHistory l where l.user.id = ?1")
	public List<LocationHistory> findByUserId(Long userId);
	
	@Transactional
	@Modifying
	@Query("delete from LocationHistory l where l.user.id = ?1")
	public void deleteByUserId(Long userId);
}
