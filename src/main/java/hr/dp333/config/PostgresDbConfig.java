package hr.dp333.config;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Validated
@Getter
@Setter
public class PostgresDbConfig {

	@NotEmpty
	private String url;

	@NotEmpty
	private String username;

	@NotNull
	private String password;

	@NotEmpty
	private String driverClassName;

	@Bean(name = "postgresDataSource")
	@Primary
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create().url(this.url).username(this.username).password(this.password).driverClassName(this.driverClassName).build();
	}

}
