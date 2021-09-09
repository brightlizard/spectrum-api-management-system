package net.brightlizard.spectrum.rest.service;

import net.brightlizard.spectrum.rest.model.ApiEntity;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public interface ApiValidator {

    void validateApiCreation(ApiEntity apiEntity) throws RuntimeException;
    void validateApiUpdate(String id, ApiEntity apiEntity) throws RuntimeException;
}
