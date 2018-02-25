package hr.dp333.config.batch;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hr.dp333.domain.County;
import hr.dp333.domain.VoterGroup;
import hr.dp333.domain.VoterRegistration;
import hr.dp333.domain.dao.CountyRepository;
import hr.dp333.domain.dao.VoterGroupRepository;
import hr.dp333.domain.dao.VoterRegistrationRepository;
import hr.dp333.enums.DataColumn;
import hr.dp333.enums.Party;
import hr.dp333.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class JsonToDb {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(Constants.JSON_DATE_FORMAT_PATTERN);

	private CountyRepository countyRepository;
	private VoterRegistrationRepository voterRegistrationRepository;
	private VoterGroupRepository voterGroupRepository;

	@PostConstruct
	private void importJson() {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(Constants.JSON_SOURCE_FILE_NAME);
			if (null == in) {
				log.error("Source file ({}) not found.", Constants.JSON_SOURCE_FILE_NAME);
				return;
			}
			JsonNode dataNode = mapper.readTree(in).get("data");
			final Set<VoterRegistration> voterRegistrationBatch = new HashSet<>();
			final Set<VoterGroup> voterGroupBatch = new HashSet<>();
			for (int i = 0; i < dataNode.size(); i++) {
				JsonNode row = dataNode.get(i);
				this.processRow(row, voterRegistrationBatch, voterGroupBatch);
				if (i % 10 == 9) {
					// save entities in batches
					this.voterRegistrationRepository.save(voterRegistrationBatch);
					voterRegistrationBatch.clear();
					this.voterGroupRepository.save(voterGroupBatch);
					voterGroupBatch.clear();
				}
			}
			if (!voterRegistrationBatch.isEmpty()) {
				this.voterRegistrationRepository.save(voterRegistrationBatch);
			}
			if (!voterGroupBatch.isEmpty()) {
				this.voterGroupRepository.save(voterGroupBatch);
			}
		} catch (final IOException e) {
			log.error("Import from JSON to DB failed! {}", e.getMessage(), e);
		}
	}

	private void processRow(final JsonNode row, final Set<VoterRegistration> voterRegistrationBatch, final Set<VoterGroup> voterGroupBatch) {
		final County county = this.initCounty(row);

		final VoterRegistration voterRegistration = this.initVoterRegistration(row, county);
		voterRegistrationBatch.add(voterRegistration);

		final Set<VoterGroup> voterGroups = this.initVoterGroups(row, voterRegistration);
		voterGroupBatch.addAll(voterGroups);
	}

	private County initCounty(JsonNode row) {
		int fips = row.get(DataColumn.COUNTY_FIPS.getRow()).asInt();
		County county = this.countyRepository.findByFips(fips);
		if (null != county) {
			return county;
		}
		String name = row.get(DataColumn.COUNTY_NAME.getRow()).asText();
		String latitude = row.get(DataColumn.COUNTY_LATITUDE.getRow()).asText();
		String longitude = row.get(DataColumn.COUNTY_LONGITUDE.getRow()).asText();

		return this.countyRepository.save(new County(name, fips, latitude, longitude));
	}

	private Set<VoterGroup> initVoterGroups(JsonNode row, VoterRegistration voterRegistration) {
		Set<VoterGroup> voterGroups = new HashSet<>();
		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.DEMOCRAT, DataColumn.DEMOCRAT_ACTIVE, true));
		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.REPUBLICAN, DataColumn.REPUBLICAN_ACTIVE, true));
		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.LIBERTARIAN, DataColumn.LIBERTARIAN_ACTIVE, true));
		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.NO_PARTY, DataColumn.NO_PARTY_ACTIVE, true));
		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.OTHER, DataColumn.OTHER_ACTIVE, true));

		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.DEMOCRAT, DataColumn.DEMOCRAT_INACTIVE, false));
		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.REPUBLICAN, DataColumn.REPUBLICAN_INACTIVE, false));
		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.LIBERTARIAN, DataColumn.LIBERTARIAN_INACTIVE, false));
		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.NO_PARTY, DataColumn.NO_PARTY_INACTIVE, false));
		voterGroups.add(this.initVoterGroup(row, voterRegistration, Party.OTHER, DataColumn.OTHER_INACTIVE, false));
		return voterGroups;
	}

	private VoterGroup initVoterGroup(JsonNode row, VoterRegistration voterRegistration, Party party, DataColumn column, boolean active) {
		return new VoterGroup(voterRegistration, party.toString(), row.get(column.getRow()).asInt(), active);
	}

	private VoterRegistration initVoterRegistration(JsonNode row, County county) {
		LocalDateTime dateTime = LocalDateTime.parse(row.get(DataColumn.VOTER_REGISTRATION_DATE.getRow()).asText(), DATE_FORMATTER);
		return new VoterRegistration(county, dateTime.toLocalDate(), row.get(DataColumn.TOTAL_ACTIVE.getRow()).asInt(),
				row.get(DataColumn.TOTAL_INACTIVE.getRow()).asInt(), row.get(DataColumn.GRAND_TOTAL.getRow()).asInt());
	}

}
