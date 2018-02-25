package hr.dp333.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.dp333.domain.County;

public interface CountyRepository extends JpaRepository<County, Long> {

	public County findByFips(int fips);

}
