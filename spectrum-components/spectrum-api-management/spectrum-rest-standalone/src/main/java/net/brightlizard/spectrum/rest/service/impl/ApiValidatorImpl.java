package net.brightlizard.spectrum.rest.service.impl;

import net.brightlizard.spectrum.repository.api.ApiRepository;
import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import net.brightlizard.spectrum.repository.model.Api;
import net.brightlizard.spectrum.rest.model.ApiEntity;
import net.brightlizard.spectrum.rest.service.ApiValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
@Component
public class ApiValidatorImpl implements ApiValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiValidatorImpl.class);
    public static final Integer CONTEXT_PATH_MIN_LENGTH = 3;

    @Autowired
    private ApiRepository apiRepository;

    @Override
    public void validateApiCreation(ApiEntity apiEntity) throws RuntimeException {
        try {
            validateTitleLength(apiEntity);
            validateVersionFormat(apiEntity);
            validateContextPath(apiEntity.getContextPath());
            validateTitleAndVersion(apiEntity);
        } catch (TechnicalException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void validateApiUpdate(String id, ApiEntity apiEntity) throws RuntimeException {
        try {
            Api api = apiRepository.findById(id).get();
            validateTitleLength(apiEntity);
            validateVersionFormat(apiEntity);
            forbidToChangeMajorVersion(apiEntity, api);
            if(isNotEqualsContextPaths(apiEntity, api)){
                validateContextPath(apiEntity.getContextPath());
            }
            if(
                !apiEntity.getTitle().equalsIgnoreCase(api.getTitle()) ||
                !apiEntity.getVersion().equalsIgnoreCase(api.getVersion())
            ){
                validateTitleAndVersion(apiEntity);
            }
        } catch (TechnicalException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void validateTitleLength(ApiEntity apiEntity){
        if(apiEntity.getTitle().length() <CONTEXT_PATH_MIN_LENGTH){
            throw new RuntimeException("API title length should be equals or longer than 3 symbols");
        }
    }

    private void validateVersionFormat(ApiEntity apiEntity){
        String regex = "^(?!\\.)(\\d+(\\.\\d+)+)(?![\\d\\.])$";
        if(!Pattern.compile(regex).matcher(apiEntity.getVersion()).find()){
            throw new RuntimeException("API version should be valid according semantic format [x.y.z]");
        }
    }

    private void forbidToChangeMajorVersion(ApiEntity apiEntity, Api api){
        String major1 = apiEntity.getVersion().split("\\.")[0];
        String major2 = api.getVersion().split("\\.")[0];
        if(isEqualsContextPaths(apiEntity, api) && !major1.equalsIgnoreCase(major2)){
            throw new RuntimeException("Forbidden to change major version of API without changing it in context_path");
        }
    }

    private void validateContextPath(String contextPath) throws TechnicalException {
        List<String> contextPaths = apiRepository.findContextPaths(contextPath.substring(0, CONTEXT_PATH_MIN_LENGTH));
        LOGGER.debug("CONTEXT PATHS -> {}", contextPaths);
        contextPaths.forEach(path -> {
            if(
                path.equalsIgnoreCase(contextPath) ||
                path.startsWith(contextPath + "/") ||
                contextPath.startsWith(path + "/")
            ){
                throw new RuntimeException(String.format("Context path %s already in use", path));
            }
        });
    }

    private void validateTitleAndVersion(ApiEntity apiEntity) throws TechnicalException {
        List<Api> apis = apiRepository.findByTitleAndVersion(apiEntity.getTitle(), apiEntity.getVersion());
        if(apis.size() > 0){
            throw new RuntimeException(String.format("API %s [%s] already exists", apiEntity.getTitle(), apiEntity.getVersion()));
        }
    }

    private boolean isEqualsContextPaths(ApiEntity apiEntity, Api api){
        return apiEntity.getContextPath().equalsIgnoreCase(api.getContextPath());
    }

    private boolean isNotEqualsContextPaths(ApiEntity apiEntity, Api api){
        return !isEqualsContextPaths(apiEntity, api);
    }
}
