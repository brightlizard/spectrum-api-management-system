package net.brightlizard.spectrum.repository.jdbc.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public class JdbcAbstractRepository extends TransactionalRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;


}
