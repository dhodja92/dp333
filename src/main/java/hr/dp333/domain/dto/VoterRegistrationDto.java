package hr.dp333.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import hr.dp333.domain.VoterGroup;
import hr.dp333.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString (of = {"countyName", "date", "grandTotal"})
public class VoterRegistrationDto implements Serializable {

	private static final long serialVersionUID = 1935715777893609090L;

	private String countyName;
	private transient LocalDate date;
	private Integer grandTotal;
	private Set<VoterGroupDto> voterGroups = new LinkedHashSet<>();

	public VoterRegistrationDto(String countyName, LocalDate date, Integer grandTotal, Set<VoterGroup> voterGroups) {
		this.countyName = countyName;
		this.date = date;
		this.grandTotal = grandTotal;
		for (final VoterGroup vg : voterGroups) {
			this.voterGroups.add(new VoterGroupDto(vg.getParty(), vg.getVoters(), vg.isActive(), vg.isActive() ? Constants.ACTIVE : Constants.INACTIVE));
		}
	}

}
