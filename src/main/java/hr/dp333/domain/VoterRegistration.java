package hr.dp333.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = { @Index(columnList = "county_id", name = "idx_county_id") })
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoterRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_voter_registration_id")
	@SequenceGenerator(name = "seq_voter_registration_id", sequenceName = "voter_registration_sequence", allocationSize = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "county_id")
	private County county;

	@Basic
	private LocalDate date;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "voterRegistration")
	@OrderBy("active DESC, party ASC")
	private Set<VoterGroup> voterGroup = new HashSet<>(0);

	private int totalActive;
	private int totalInactive;
	private int grandTotal;

	public VoterRegistration(County county, LocalDate date, int totalActive, int totalInactive, int grandTotal) {
		this.county = county;
		this.date = date;
		this.totalActive = totalActive;
		this.totalInactive = totalInactive;
		this.grandTotal = grandTotal;
	}

	@Override
	public String toString() {
		return "VoterRegistration(id=" + this.id + ",countyName=" + this.county.getName() + ",date=" + this.date + ",totalActive=" + this.totalActive
				+ ",totalInactive=" + this.totalInactive + ",grandTotal=" + this.grandTotal + ")";
	}

}
