package net.brightlizard.spectrum.repository.jdbc;

import net.brightlizard.spectrum.repository.jdbc.common.AbstractJdbcRepositoryConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
@Configuration
@ComponentScan(basePackages = { "net.brightlizard.spectrum.repository.*" })
public class JdbcManagementRepositoryConfiguration extends AbstractJdbcRepositoryConfiguration {


}