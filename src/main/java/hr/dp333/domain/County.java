package hr.dp333.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(indexes = { @Index(columnList = "fips", name = "idx_fips") })
@Getter
@ToString(of = { "id", "fips", "name" })
@NoArgsConstructor
@AllArgsConstructor
public class County {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_county_id")
	@SequenceGenerator(name = "seq_county_id", sequenceName = "county_sequence", allocationSize = 1)
	private Long id;

	@Column(length = 100)
	private String name;

	private int fips;

	@Column(length = 20)
	private String latitude;

	@Column(length = 20)
	private String longitude;

	public County(String name, int fips, String latitude, String longitude) {
		this.name = name;
		this.fips = fips;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}