package hr.dp333.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.dp333.domain.VoterRegistration;

public interface VoterRegistrationRepository extends JpaRepository<VoterRegistration, Long> {

}
