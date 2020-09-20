package biggusrichus.locationapi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Location History entity.
 * 
 * @author richard.brooks
 *
 */
@Entity
public class LocationHistory {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	private LocalDateTime recordedDate;
	
	@NotNull
	@DecimalMin(value="-90.0", inclusive = true)
	@DecimalMax(value="90.0", inclusive = true)
	@Column(precision=8, scale=6)
	private BigDecimal latitude;
	
	@NotNull
	@DecimalMin(value="-180.0", inclusive = true)
	@DecimalMax(value="180.0", inclusive = true)
	@Column(precision=9, scale=6)
	private BigDecimal longitude;
	
	public LocationHistory() {}
	
	public LocationHistory(User user, LocalDateTime recordedDate, BigDecimal latitude, BigDecimal longitude) {
		this.user = user;
		this.recordedDate = recordedDate;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getRecordedDate() {
		return recordedDate;
	}

	public void setRecordedDate(LocalDateTime recordedDate) {
		this.recordedDate = recordedDate;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((recordedDate == null) ? 0 : recordedDate.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationHistory other = (LocationHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (recordedDate == null) {
			if (other.recordedDate != null)
				return false;
		} else if (!recordedDate.equals(other.recordedDate))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocationHistory [id=" + id + ", user=" + user + ", recordedDate=" + recordedDate + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}
}
