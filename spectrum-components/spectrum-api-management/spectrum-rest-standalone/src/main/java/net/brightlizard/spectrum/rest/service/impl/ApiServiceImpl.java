package net.brightlizard.spectrum.rest.service.impl;

import net.brightlizard.spectrum.repository.api.ApiRepository;
import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import net.brightlizard.spectrum.repository.model.Api;
import net.brightlizard.spectrum.rest.model.ApiEntity;
import net.brightlizard.spectrum.rest.service.ApiService;
import net.brightlizard.spectrum.rest.service.ApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
@Component
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private ApiValidator apiValidator;

    @Override
    public List<ApiEntity> listApis() throws TechnicalException {
        return apiRepository.findAll()
                            .get()
                            .stream()
                            .map(api -> new ApiEntity(api))
                            .collect(Collectors.toList());
    }

    @Override
    public ApiEntity create(ApiEntity newApi) throws TechnicalException {
        apiValidator.validateApiCreation(newApi);
        Api repoApi = convert(newApi);
        return new ApiEntity(apiRepository.create(repoApi));
    }

    @Override
    public ApiEntity update(String id, ApiEntity api) throws TechnicalException {
        api.setId(id);
        apiValidator.validateApiUpdate(id, api);
        Api repoApi = convert(id, api);
        return new ApiEntity(apiRepository.update(repoApi));
    }

    private Api convert(String id, ApiEntity api) {
        Api convertedApi = convert(api);
        convertedApi.setId(id);
        return convertedApi;
    }

    private Api convert(ApiEntity api) {
        Api repoApi = new Api();
        repoApi.setTitle(api.getTitle());
        repoApi.setVersion(api.getVersion());
        repoApi.setDescription(api.getDescription());
        repoApi.setContextPath(api.getContextPath());
        repoApi.setSpecId(UUID.randomUUID().toString());
        return repoApi;
    }
}
