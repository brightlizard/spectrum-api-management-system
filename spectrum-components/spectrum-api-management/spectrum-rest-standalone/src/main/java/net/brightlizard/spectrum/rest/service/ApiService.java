package net.brightlizard.spectrum.rest.service;

import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import net.brightlizard.spectrum.rest.model.Api;

import java.util.List;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public interface ApiService {

    List<Api> listApis() throws TechnicalException;
    Api create(Api newApi) throws TechnicalException;

}
