package hr.dp333.util;

import com.querydsl.core.BooleanBuilder;

import hr.dp333.domain.QVoterGroup;
import hr.dp333.domain.QVoterRegistration;
import hr.dp333.enums.Party;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VoterRegistrationRequest {

	private final BooleanBuilder filter;
	private final BooleanBuilder countFilter;
	private final long limit;
	private final long offset;

	public VoterRegistrationRequest(String countyName, Integer month, String party, Boolean active, Integer pageNumber, Integer limit) {
		final QVoterRegistration vr = QVoterRegistration.voterRegistration;
		final QVoterGroup vg = QVoterGroup.voterGroup;
		this.filter = new BooleanBuilder();
		this.countFilter = new BooleanBuilder();
		if (null != countyName) {
			this.filter.and(vr.county.name.equalsIgnoreCase(countyName));
			this.countFilter.and(vr.county.name.equalsIgnoreCase(countyName));
		}
		if (null != month && (month >= 1 && month <= 12)) {
			this.filter.and(vr.date.month().eq(month));
			this.countFilter.and(vr.date.month().eq(month));
		}
		if (Party.isValidParty(party)) {
			this.filter.and(vg.party.equalsIgnoreCase(party));
			this.countFilter.and(vr.voterGroup.any().party.equalsIgnoreCase(party));
		}
		if (null != active) {
			this.filter.and(vg.active.eq(active));
			this.countFilter.and(vr.voterGroup.any().active.eq(active));
		}
		this.limit = null != limit && (limit > 0 && limit <= 50) ? limit : 50;
		this.offset = null != pageNumber && pageNumber > 0 ? (pageNumber * limit) : 0;
	}

}
