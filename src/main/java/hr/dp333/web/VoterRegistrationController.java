package hr.dp333.web;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hr.dp333.domain.dto.DtoTransferObject;
import hr.dp333.domain.dto.VoterRegistrationDto;
import hr.dp333.service.VoterRegistrationService;
import hr.dp333.util.Constants;
import hr.dp333.util.VoterRegistrationFilter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class VoterRegistrationController {

	private VoterRegistrationService voterRegistrationService;

	@RequestMapping("/get_voters_where")
	public String getVotersWhere(Model model,
			@RequestParam(value = "county", required = false) String countyName,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "party", required = false) String party,
			@RequestParam(value = "active", required = false) Boolean active,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		VoterRegistrationFilter filter = new VoterRegistrationFilter(countyName, month, party, active, page, limit);
		DtoTransferObject<VoterRegistrationDto> voterRegistrations = this.voterRegistrationService.filterVoterRegistrations(filter);

		model.addAttribute("voterRegistrations", voterRegistrations);
		model.addAttribute("columns", this.getHeaderColumns(filter));
		return "voters";
	}

	private Set<String> getHeaderColumns(VoterRegistrationFilter filter) {
		Set<String> columnNames = new LinkedHashSet<>(Constants.HEADER_COLUMNS);
		if (null != filter.getParty()) {
			columnNames.removeIf(p -> !p.startsWith(filter.getParty()));
		}
		if (null != filter.getActive()) {
			columnNames.removeIf(p -> !p.endsWith(filter.getActive() ? Constants.ACTIVE : Constants.INACTIVE));
		}
		return columnNames;
	}

}
