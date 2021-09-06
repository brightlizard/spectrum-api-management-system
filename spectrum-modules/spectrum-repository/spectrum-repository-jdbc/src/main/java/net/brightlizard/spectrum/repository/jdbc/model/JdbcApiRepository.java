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
            api.setSpecId(resultSet.getString("specId"));
            return api;
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
            throw new TechnicalException("Failed to find all apis", ex);
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
            LOGGER.error("Failed to find apis by id:", ex);
            throw new TechnicalException("Failed to find api by id", ex);
        }
    }

    @Override
    public Api create(Api api) throws TechnicalException {
        LOGGER.debug("JdbcApiRepository.create({})", api);

        String SQL_COUNT = "SELECT count(*) FROM apis WHERE title = ? AND version = ?";
        Integer count = jdbcTemplate.queryForObject(
                                        SQL_COUNT,
                                        new Object[]{api.getTitle(), api.getVersion()},
                                        Integer.class);

        if(count > 0){
            throw new TechnicalException(String.format("Api with name \"%s\" [%s] already exists", api.getTitle(), api.getVersion()));
        }

        String SQL = "INSERT INTO apis (id, title, version, description, specid) VALUES (?,?,?,?,?)";
        String id = UUID.randomUUID().toString();
        jdbcTemplate.update(SQL, id, api.getTitle(), api.getVersion(), api.getDescription(), api.getSpecId());
        return findById(id).orElse(null);
    }

    @Override
    public Api update(Api item) throws TechnicalException {
        return null;
    }

    @Override
    public void delete(String s) throws TechnicalException {

    }
}
