package net.brightlizard.spectrum.rest.spring;

import net.brightlizard.spectrum.repository.jdbc.JdbcManagementRepositoryConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@Import(JdbcManagementRepositoryConfiguration.class)
@ComponentScan(basePackages = {
    "net.brightlizard.spectrum.rest",
    "net.brightlizard.spectrum.repository",
    "net.brightlizard.spectrum.repository.jdbc"
})
public class RestConfiguration extends WebMvcConfigurationSupport {

}
