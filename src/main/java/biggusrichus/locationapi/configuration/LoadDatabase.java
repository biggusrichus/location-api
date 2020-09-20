package biggusrichus.locationapi.configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import biggusrichus.locationapi.entity.LocationHistory;
import biggusrichus.locationapi.entity.User;
import biggusrichus.locationapi.repository.LocationHistoryRepository;
import biggusrichus.locationapi.repository.UserRepository;

/**
 * Configuration class to preload a set of users and location histories.
 * 
 * @author richard.brooks
 *
 */
@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, LocationHistoryRepository locationHistoryRepository) {
		return args -> {
			User user1 = new User("Richard King", new BigDecimal("51.1"), new BigDecimal("-1.4"));
			User user2 = new User("William Moore", new BigDecimal("51.2"), new BigDecimal("-1.5"));
			User user3 = new User("Steve Thompson", new BigDecimal("51.3"), new BigDecimal("-1.6"));
			User user4 = new User("Gerry Hill", new BigDecimal("51.4"), new BigDecimal("-1.7"));

			log.info("Preloading " + userRepository.save(user1));
			log.info("Preloading " + userRepository.save(user2));
			log.info("Preloading " + userRepository.save(user3));
			log.info("Preloading " + userRepository.save(user4));

			LocalDateTime dateTime = LocalDateTime.of(2020, 9, 18, 9, 0, 0);
			
			log.info("Preloading " + locationHistoryRepository
					.save(new LocationHistory(user1, dateTime, new BigDecimal("51.1"), new BigDecimal("-1.4"))));
			log.info("Preloading " + locationHistoryRepository
					.save(new LocationHistory(user1, dateTime.minusHours(3), new BigDecimal("51.13"), new BigDecimal("-1.45"))));
			log.info("Preloading " + locationHistoryRepository
					.save(new LocationHistory(user1, dateTime.minusDays(1), new BigDecimal("51.18"), new BigDecimal("-1.456"))));
			log.info("Preloading " + locationHistoryRepository
					.save(new LocationHistory(user2, dateTime, new BigDecimal("51.2"), new BigDecimal("-1.5"))));
			log.info("Preloading " + locationHistoryRepository
					.save(new LocationHistory(user3, dateTime, new BigDecimal("51.3"), new BigDecimal("-1.6"))));
			log.info("Preloading " + locationHistoryRepository
					.save(new LocationHistory(user4, dateTime, new BigDecimal("51.4"), new BigDecimal("-1.7"))));
		};
	}

}
