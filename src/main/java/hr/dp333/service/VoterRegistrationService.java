package hr.dp333.service;

import hr.dp333.domain.VoterRegistration;
import hr.dp333.domain.dto.DtoTransferObject;
import hr.dp333.domain.dto.VoterRegistrationDto;
import hr.dp333.util.VoterRegistrationFilter;
import hr.dp333.util.VoterRegistrationRequest;

public interface VoterRegistrationService {

	public DtoTransferObject<VoterRegistration> filter(VoterRegistrationRequest request);

	public DtoTransferObject<VoterRegistrationDto> filter(VoterRegistrationFilter filter);

}
