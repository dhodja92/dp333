package hr.dp333.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import hr.dp333.domain.VoterRegistration;
import hr.dp333.domain.projection.VoterRegistrationProjection;

@RepositoryRestResource(excerptProjection = VoterRegistrationProjection.class)
public interface VoterRegistrationRepository extends JpaRepository<VoterRegistration, Long>, QueryDslPredicateExecutor<VoterRegistration> {

}
