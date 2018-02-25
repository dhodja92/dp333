package hr.dp333.domain.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import hr.dp333.domain.VoterGroup;

@Projection(name = "simpleVoterGroup", types = { VoterGroup.class })
public interface VoterGroupProjection {

	@Value("#{target.getParty()} - #{target.isActive() ? 'Active' : 'Inactive'}")
	String getParty();

	int getVoters();

}
