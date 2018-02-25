package hr.dp333.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import hr.dp333.domain.VoterRegistration;
import hr.dp333.domain.dto.DtoTransferObject;
import hr.dp333.domain.dto.VoterRegistrationDto;
import hr.dp333.util.VoterRegistrationFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class VoterRegistrationServiceImpl implements VoterRegistrationService {

	private EntityManager entityManager;

	@Override
	public DtoTransferObject<VoterRegistrationDto> filterVoterRegistrations(VoterRegistrationFilter filter) {
		long start = System.currentTimeMillis();
		log.debug("Start: filterVoterRegistrations ({})", filter);
		List<VoterRegistrationDto> dtos = new ArrayList<>();

		TypedQuery<VoterRegistration> query = this.buildEntitiesQuery(filter);
		List<VoterRegistration> result = query.getResultList();
		for (final VoterRegistration vr : result) {
			dtos.add(new VoterRegistrationDto(vr.getCounty().getName(), vr.getDate(), vr.getGrandTotal(), vr.getVoterGroup()));
		}

		TypedQuery<Long> countQuery = this.buildCountQuery(filter);
		Long count = countQuery.getSingleResult();

		log.debug("End: filterVoterRegistrations, time: {} ms.", System.currentTimeMillis() - start);
		return new DtoTransferObject<>(dtos, count);
	}

	private TypedQuery<VoterRegistration> buildEntitiesQuery(VoterRegistrationFilter filter) {
		Map<String, Object> params = new HashMap<>();

		String queryString = this.buildEntitiesQueryString(filter, params);

		TypedQuery<VoterRegistration> query = this.entityManager.createQuery(queryString, VoterRegistration.class);
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		Integer limit = null == filter.getLimit() ? 100 : filter.getLimit();
		Integer startPosition = null == filter.getPage() ? 0 : (filter.getPage() - 1) * limit;
		query.setMaxResults(limit);
		query.setFirstResult(startPosition);
		return query;
	}

	private TypedQuery<Long> buildCountQuery(VoterRegistrationFilter filter) {
		Map<String, Object> params = new HashMap<>();

		String queryString = this.buildCountQueryString(filter, params);

		TypedQuery<Long> query = this.entityManager.createQuery(queryString, Long.class);
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query;
	}

	private String buildEntitiesQueryString(VoterRegistrationFilter filter, Map<String, Object> params) {
		StringBuilder sb = new StringBuilder("SELECT vr FROM VoterRegistration vr JOIN vr.county c JOIN FETCH vr.voterGroup vg WHERE 1 = 1");
		if (null != filter.getCountyName()) {
			sb.append(" AND c.name = :cname");
			params.put("cname", filter.getCountyName());
		}
		if (null != filter.getMonth()) {
			sb.append(" AND MONTH(vr.date) = :month");
			params.put("month", filter.getMonth());
		}
		if (null != filter.getParty()) {
			sb.append(" AND vg.party = :party");
			params.put("party", filter.getParty());
		}
		if (null != filter.getActive()) {
			sb.append(" AND vg.active = :active");
			params.put("active", filter.getActive());
		}
		sb.append(" ORDER BY c.name");
		return sb.toString();
	}

	private String buildCountQueryString(VoterRegistrationFilter filter, Map<String, Object> params) {
		StringBuilder sb = new StringBuilder("SELECT COUNT(vr) FROM VoterRegistration vr JOIN vr.county c WHERE 1 = 1");
		if (null != filter.getCountyName()) {
			sb.append(" AND c.name = :cname");
			params.put("cname", filter.getCountyName());
		}
		if (null != filter.getMonth()) {
			sb.append(" AND MONTH(vr.date) = :month");
			params.put("month", filter.getMonth());
		}
		return sb.toString();
	}

}
