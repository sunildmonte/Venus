package org.venus.infra.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.venus.domain.user.entity.User;
import org.venus.infra.config.database.DatabaseConfig;
import org.venus.infra.config.properties.AppProperties;

@Configuration
@ComponentScan(
		basePackageClasses = {AppProperties.class, User.class, DatabaseConfig.class}
		//{"org.venus.domain", "org.venus.config.properties"}
		)
public class AppConfig {

}
