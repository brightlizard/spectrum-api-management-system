package net.brightlizard.spectrum.rest.service;

import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import net.brightlizard.spectrum.rest.model.ApiEntity;
import java.util.List;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public interface ApiService {

    List<ApiEntity> listApis() throws TechnicalException;
    ApiEntity create(ApiEntity newApi) throws TechnicalException;
    ApiEntity update(String id, ApiEntity api) throws TechnicalException;
    void delete(String id) throws TechnicalException;
}
