package com.rightpair.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EntityScan(basePackages = "com.rightpair.core.domain")
@EnableJpaRepositories(basePackages = "com.rightpair.core.repository")
@Configuration
public class JpaAuditingConfig {
}
