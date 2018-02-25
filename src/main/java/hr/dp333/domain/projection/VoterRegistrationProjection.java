package hr.dp333.domain.projection;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import hr.dp333.domain.VoterRegistration;

@Projection(name = "simpleVoterRegistration", types = { VoterRegistration.class })
public interface VoterRegistrationProjection {

	@Value("#{target.county.getName()}")
	String getCounty();

	@Value("#{target.getFormattedDate()}")
	String getDate();

	int getTotalActive();

	int getTotalInactive();

	int getGrandTotal();

	Set<VoterGroupProjection> getVoterGroup();

}
