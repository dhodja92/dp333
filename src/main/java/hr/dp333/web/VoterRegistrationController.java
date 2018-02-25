package hr.dp333.web;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hr.dp333.domain.VoterRegistration;
import hr.dp333.domain.dto.DtoTransferObject;
import hr.dp333.domain.dto.VoterRegistrationDto;
import hr.dp333.enums.Party;
import hr.dp333.service.VoterRegistrationService;
import hr.dp333.util.Constants;
import hr.dp333.util.VoterRegistrationFilter;
import hr.dp333.util.VoterRegistrationRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class VoterRegistrationController {

	private VoterRegistrationService voterRegistrationService;

	/**
	 * Filter voters using manual query building approach.
	 * 
	 * @param model
	 * @param countyName
	 * @param month
	 * @param party
	 * @param active
	 * @param page
	 * @param limit
	 * @return voters
	 */
	@RequestMapping("/get_voters_where")
	public String getVotersWhere(Model model,
			@RequestParam(value = "county", required = false) String countyName,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "party", required = false) String party,
			@RequestParam(value = "active", required = false) Boolean active,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		VoterRegistrationFilter filter = new VoterRegistrationFilter(countyName, month, party, active, page, limit);
		DtoTransferObject<VoterRegistrationDto> voterRegistrations = this.voterRegistrationService.filter(filter);

		model.addAttribute("voterRegistrations", voterRegistrations);
		model.addAttribute("columns", this.getHeaderColumns(party, active));
		return "voters";
	}

	/**
	 * Filter voters using QueryDsl.
	 * 
	 * @param model
	 * @param countyName
	 * @param month
	 * @param party
	 * @param active
	 * @param page
	 * @param limit
	 * @return voters
	 */
	@RequestMapping("/get_voters_where_querydsl")
	public String getVotersWhereQueryDsl(Model model,
			@RequestParam(value = "county", required = false) String countyName,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "party", required = false) String party,
			@RequestParam(value = "active", required = false) Boolean active,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		VoterRegistrationRequest request = new VoterRegistrationRequest(countyName, month, party, active, page, limit);
		DtoTransferObject<VoterRegistration> voterRegistrations = this.voterRegistrationService.filter(request);

		model.addAttribute("voterRegistrations", voterRegistrations);
		model.addAttribute("columns", this.getHeaderColumns(party, active));
		return "votersqdsl";
	}

	private Set<String> getHeaderColumns(String party, Boolean active) {
		Set<String> columnNames = new LinkedHashSet<>(Constants.HEADER_COLUMNS);
		if (Party.isValidParty(party)) {
			columnNames.removeIf(p -> !p.startsWith(party));
		}
		if (null != active) {
			columnNames.removeIf(p -> !p.endsWith(active ? Constants.ACTIVE : Constants.INACTIVE));
		}
		return columnNames;
	}

}
