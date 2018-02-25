package hr.dp333.service;

import hr.dp333.domain.dto.DtoTransferObject;
import hr.dp333.domain.dto.VoterRegistrationDto;
import hr.dp333.util.VoterRegistrationFilter;

public interface VoterRegistrationService {

	public DtoTransferObject<VoterRegistrationDto> filterVoterRegistrations(VoterRegistrationFilter filter);

}
