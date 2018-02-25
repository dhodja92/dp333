package hr.dp333.util;

import hr.dp333.enums.Party;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VoterRegistrationFilter {

	private String countyName;
	private Integer month;
	private String party;
	private Boolean active;
	private Integer page;
	private Integer limit;

	public VoterRegistrationFilter(String countyName, Integer month, String party, Boolean active, Integer page, Integer limit) {
		this.countyName = countyName;
		if (null != month && (month >= 1 && month <= 12)) {
			this.month = month;
		}
		if (Party.isValidParty(party)) {
			this.party = party;
		}
		this.active = active;
		if (null != page && page > 0) {
			this.page = page;
		}
		this.limit = limit;
	}

}
