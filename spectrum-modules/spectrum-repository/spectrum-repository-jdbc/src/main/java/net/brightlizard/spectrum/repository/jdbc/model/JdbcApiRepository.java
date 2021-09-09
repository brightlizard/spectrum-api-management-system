package net.brightlizard.spectrum.repository.jdbc.model;

import net.brightlizard.spectrum.repository.api.ApiRepository;
import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import net.brightlizard.spectrum.repository.model.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
@Repository
public class JdbcApiRepository extends JdbcAbstractRepository implements ApiRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcApiRepository.class);

    public class ApiRowMapper implements RowMapper<Api> {

        @Override
        public Api mapRow(ResultSet resultSet, int i) throws SQLException {
            Api api = new Api();
            api.setId(resultSet.getString("id"));
            api.setTitle(resultSet.getString("title"));
            api.setVersion(resultSet.getString("version"));
            api.setDescription(resultSet.getString("description"));
            api.setContextPath(resultSet.getString("context_path"));
            api.setSpecId(resultSet.getString("specId"));
            return api;
        }

    }

    public class ContextPathMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString("context_path");
        }

    }

    @Override
    public Optional<List<Api>> findAll() throws TechnicalException {
        LOGGER.debug("JdbcApiRepository.findAll()");
        try {
            String SQL = "SELECT * FROM apis";
            List apis = jdbcTemplate.query(SQL, new ApiRowMapper());
            return Optional.ofNullable(apis);
        } catch (final Exception ex) {
            LOGGER.error("Failed to find all apis:", ex);
            throw new TechnicalException(ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<Api> findById(String id) throws TechnicalException {
        LOGGER.debug("JdbcApiRepository.findById({})", id);
        try {
            String SQL = "SELECT * FROM apis WHERE id = ?";
            Api api = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ApiRowMapper());
            return Optional.ofNullable(api);
        } catch (final Exception ex) {
            LOGGER.error("Failed to find api by id:", ex);
            throw new TechnicalException(String.format("Failed to find api by id = %d", id), ex);
        }
    }

    @Override
    public Api create(Api api) throws TechnicalException {
        LOGGER.debug("JdbcApiRepository.create({})", api);
        try {
            String SQL = "INSERT INTO apis (id, title, version, description, context_path, specid) VALUES (?,?,?,?,?,?)";
            String id = UUID.randomUUID().toString();
            jdbcTemplate.update(SQL, id, api.getTitle(), api.getVersion(), api.getDescription(), api.getContextPath(), api.getSpecId());
            return findById(id).orElse(null);
        } catch (final Exception ex) {
            LOGGER.error("Failed to create an api:", ex);
            throw new TechnicalException(ex.getMessage(), ex);
        }
    }

    @Override
    public Api update(Api api) throws TechnicalException {
        LOGGER.debug("JdbcApiRepository.update({})", api);
        try {
            String SQL = "UPDATE apis SET title = ?, version = ?, description = ?, context_path = ? WHERE id = ?";
            jdbcTemplate.update(SQL, api.getTitle(), api.getVersion(), api.getDescription(), api.getContextPath(), api.getId());
            return findById(api.getId()).orElse(null);
        } catch (final Exception ex) {
            LOGGER.error("Failed to update an api:", ex);
            throw new TechnicalException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean delete(String id) throws TechnicalException {
        LOGGER.debug("JdbcApiRepository.delete({})", id);
        try {
            String SQL = "DELETE FROM apis WHERE id = ?";
            return jdbcTemplate.update(SQL, id) == 1;
        } catch (final Exception ex) {
            LOGGER.error("Failed to delete an api:", ex);
            throw new TechnicalException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<String> findContextPaths(String v) throws TechnicalException {
        LOGGER.debug("JdbcApiRepository.findContextPaths({})", v);
        try {
            String SQL = "SELECT context_path FROM apis WHERE context_path LIKE ?";
            List paths = jdbcTemplate.query(SQL, new Object[]{ v + "%" }, new ContextPathMapper());
            return paths;
        } catch (final Exception ex) {
            LOGGER.error("Failed to find context paths:", ex);
            throw new TechnicalException(ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<String> findContextPathById(String id) throws TechnicalException {
        LOGGER.debug("JdbcApiRepository.findContextPathById({})", id);
        try {
            String SQL = "SELECT context_path FROM apis WHERE id = ?";
            String path = jdbcTemplate.queryForObject(SQL, new Object[]{ id }, new ContextPathMapper());
            return Optional.ofNullable(path);
        } catch (final Exception ex) {
            LOGGER.error("Failed to find context path by id:", ex);
            throw new TechnicalException(String.format("Failed to find context by id = %d", id), ex);
        }
    }

    @Override
    public List<Api> findByTitleAndVersion(String title, String version) throws TechnicalException {
        LOGGER.debug("JdbcApiRepository.findByNameAndVersion({}, {})", title, version);
        try {
            String SQL = "SELECT * FROM apis WHERE title = ? AND version = ?";
            List<Api> apis = jdbcTemplate.query(SQL, new Object[]{title, version}, new ApiRowMapper());
            return apis;
        } catch (final Exception ex) {
            LOGGER.error("Failed to find api by title and version:", ex);
            throw new TechnicalException(ex.getMessage(), ex);
        }
    }
}
