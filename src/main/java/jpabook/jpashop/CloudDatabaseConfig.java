package jpabook.jpashop;

import com.zaxxer.hikari.HikariDataSource;
import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.jdbc.CfJdbcEnv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

//@Configuration
//@Profile("cloud")
public class CloudDatabaseConfig extends AbstractCloudConfig {

//    @Bean
//    public DataSource dataSource(@Value("${hana.url}") final String url,
//                                 @Value("${hana.user}") final String user,
//                                 @Value("${hana.password}") final String password) {
//        return DataSourceBuilder.create()
//                .type(HikariDataSource.class)
//                .driverClassName(com.sap.db.jdbc.Driver.class.getName())
//                .url(url)
//                .username(user)
//                .password(password)
//                .build();
//    }

    @Bean
	@Primary
	@Profile("cloud")
	public DataSourceProperties dataSourceProperties() {
		CfJdbcEnv cfJdbcEnv = new CfJdbcEnv();
		DataSourceProperties properties = new DataSourceProperties();
		CfCredentials hanaCredentials = cfJdbcEnv.findCredentialsByName("spring-spring-db-hdi-container-P19wFoCsdynNgCV4L1s");

		if (hanaCredentials != null) {

			String uri = hanaCredentials.getUri("hana");
			properties.setUrl(uri);
			properties.setUsername(hanaCredentials.getUsername());
			properties.setPassword(hanaCredentials.getPassword());
		}

		return properties;
	}

}
