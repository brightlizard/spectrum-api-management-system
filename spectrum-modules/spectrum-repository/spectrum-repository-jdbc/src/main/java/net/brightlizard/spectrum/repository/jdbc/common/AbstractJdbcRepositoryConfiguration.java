package net.brightlizard.spectrum.repository.jdbc.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public abstract class AbstractJdbcRepositoryConfiguration implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJdbcRepositoryConfiguration.class);

    private static final boolean DEFAULT_AUTO_COMMIT = true;
    private static final long DEFAULT_CONNECTION_TIMEOUT = 10000;
    private static final long DEFAULT_IDLE_TIMEOUT = 600000;
    private static final long DEFAULT_MAX_LIFETIME = 1800000;
    private static final int DEFAULT_MIN_IDLE = 10;
    private static final int DEFAULT_MAX_POOL_SIZE = 10;
    private static final boolean DEFAULT_REGISTER_MBEANS = false;
    private static final boolean LIQUIBASE_ENABLED = true;

    @Autowired
    private Environment env;

    // Default escape char for reserved keywords
    private static char escapeReservedWordsPrefixChar = '`';
    private static char escapeReservedWordsSufixChar = '`';

    private static final String POSTGRESQL_DRIVER_TYPE = "postgresql";
    private static final String SQLSERVER_DRIVER_TYPE = "sqlserver";

    private static final String DEFAULT_PAGING_QUERY = "LIMIT %d OFFSET %d ";
    private static final String MSSQL_PAGING_QUERY = "OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ";

    private static String pagingQuery = DEFAULT_PAGING_QUERY;

    public static String escapeReservedWord(final String word) {
        return escapeReservedWordsPrefixChar + word + escapeReservedWordsSufixChar;
    }

    public static String createPagingClause(final int limit, final int offset) {
        if (pagingQuery.startsWith("OFFSET")) {
            return String.format(pagingQuery, offset, limit);
        } else {
            return String.format(pagingQuery, limit, offset);
        }
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        LOGGER.info("AbstractJdbcRepositoryConfiguration.setApplicationContext({})", applicationContext);
        final ConfigurableApplicationContext appContext;
        final ApplicationContext applicationContextParent = applicationContext.getParent();
        if (applicationContextParent == null) {
            appContext = (ConfigurableApplicationContext) applicationContext;
        } else {
            appContext = (ConfigurableApplicationContext) applicationContextParent;
        }
        final Map<String, DataSource> dataSources = appContext.getBeansOfType(DataSource.class);
        if (dataSources.isEmpty()) {
            final ConfigurableListableBeanFactory beanFactory = appContext.getBeanFactory();
            final DataSource dataSource = spectrumDataSource();
            beanFactory.registerSingleton("spectrumDataSource", dataSource);
            beanFactory.registerSingleton("spectrumTransactionManager", new DataSourceTransactionManager(dataSource));
        }
    }

    private synchronized DataSource spectrumDataSource() {
        final HikariConfig dsConfig = new HikariConfig();
        dsConfig.setPoolName("spectrum-jdbc-pool-1");

        final String jdbcUrl = readPropertyValue("spectrum.management.jdbc.url");
        setEscapeReservedWordFromJDBCUrl(jdbcUrl);

        dsConfig.setJdbcUrl(jdbcUrl);
        dsConfig.setUsername(readPropertyValue("spectrum.management.jdbc.username"));
        dsConfig.setPassword(readPropertyValue("spectrum.management.jdbc.password", false));
        final String schema = env.getProperty("spectrum.management.jdbc.schema", String.class);
        if (schema != null) {
            dsConfig.setSchema(schema);
        }
        // Pooling
        dsConfig.setAutoCommit(readPropertyValue("spectrum.management.jdbc.pool.autoCommit", Boolean.class, DEFAULT_AUTO_COMMIT));
        dsConfig.setConnectionTimeout(readPropertyValue("spectrum.management.jdbc.pool.connectionTimeout", Long.class, DEFAULT_CONNECTION_TIMEOUT));
        dsConfig.setIdleTimeout(readPropertyValue("spectrum.management.jdbc.pool.idleTimeout", Long.class, DEFAULT_IDLE_TIMEOUT));
        dsConfig.setMaxLifetime(readPropertyValue("spectrum.management.jdbc.pool.maxLifetime", Long.class, DEFAULT_MAX_LIFETIME));
        dsConfig.setMinimumIdle(readPropertyValue("spectrum.management.jdbc.pool.minIdle", Integer.class, DEFAULT_MIN_IDLE));
        dsConfig.setMaximumPoolSize(readPropertyValue("spectrum.management.jdbc.pool.maxPoolSize", Integer.class, DEFAULT_MAX_POOL_SIZE));
        dsConfig.setRegisterMbeans(readPropertyValue("spectrum.management.jdbc.pool.registerMbeans", Boolean.class, DEFAULT_REGISTER_MBEANS));


        final DataSource dataSource = new HikariDataSource(dsConfig);
        return dataSource;
    }

    public static void setEscapeReservedWordFromJDBCUrl(final String jdbcUrl) {
        if (jdbcUrl != null) {
            String[] tokenizedJdbcUrl = jdbcUrl.split(":");
            String databaseType = tokenizedJdbcUrl[1];
            //for TestContainers
            if ("tc".equals(databaseType)) {
                databaseType = tokenizedJdbcUrl[2];
            }

            switch (databaseType) {
                case POSTGRESQL_DRIVER_TYPE:
                    escapeReservedWordsPrefixChar = '\"';
                    escapeReservedWordsSufixChar = '\"';
                    break;
                case SQLSERVER_DRIVER_TYPE:
                    escapeReservedWordsPrefixChar = '[';
                    escapeReservedWordsSufixChar = ']';
                    pagingQuery = MSSQL_PAGING_QUERY;
                    break;
            }
        }
    }

    @Bean
    public JdbcTemplate spectrumJdbcTemplate(final DataSource dataSource) {
        LOGGER.debug("AbstractJdbcRepositoryConfiguration.spectrumJdbcTemplate()");
        return new JdbcTemplate(dataSource);
    }

    private String readPropertyValue(String propertyName) {
        return readPropertyValue(propertyName, true);
    }

    private String readPropertyValue(String propertyName, final boolean displayOnLog) {
        return readPropertyValue(propertyName, String.class, null, displayOnLog);
    }

    private <T> T readPropertyValue(String propertyName, Class<T> propertyType, T defaultValue) {
        return readPropertyValue(propertyName, propertyType, defaultValue, true);
    }

    private <T> T readPropertyValue(String propertyName, Class<T> propertyType, T defaultValue, final boolean displayOnLog) {
        final T value = env.getProperty(propertyName, propertyType, defaultValue);
        LOGGER.info("Reading property {}: {}", propertyName, displayOnLog ? value : "********");
        return value;
    }

}