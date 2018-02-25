package hr.dp333.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(indexes = { @Index(columnList = "party") })
@Getter
@ToString(of = { "id", "party", "voters", "active" })
@NoArgsConstructor
@AllArgsConstructor
public class VoterGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_voter_group_id")
	@SequenceGenerator(name = "seq_voter_group_id", sequenceName = "voter_group_sequence", allocationSize = 1)
	private Long id;

	@ManyToOne
	private VoterRegistration voterRegistration;

	@Column(length = 50)
	private String party;

	private int voters;

	private boolean active;

	public VoterGroup(VoterRegistration voterRegistration, String party, int voters, boolean active) {
		this.voterRegistration = voterRegistration;
		this.party = party;
		this.voters = voters;
		this.active = active;
	}

}
