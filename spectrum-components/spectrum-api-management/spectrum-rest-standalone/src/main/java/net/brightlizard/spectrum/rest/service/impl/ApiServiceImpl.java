package net.brightlizard.spectrum.rest.service.impl;

import net.brightlizard.spectrum.repository.api.ApiRepository;
import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import net.brightlizard.spectrum.rest.model.Api;
import net.brightlizard.spectrum.rest.service.ApiService;
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

    @Override
    public List<Api> listApis() throws TechnicalException {
        return apiRepository.findAll()
                            .get()
                            .stream()
                            .map(api -> new Api(api))
                            .collect(Collectors.toList());
    }

    @Override
    public Api create(Api newApi) throws TechnicalException {
        net.brightlizard.spectrum.repository.model.Api repoApi = new net.brightlizard.spectrum.repository.model.Api();
        repoApi.setTitle(newApi.getTitle());
        repoApi.setVersion(newApi.getVersion());
        repoApi.setDescription(newApi.getDescription());
        repoApi.setSpecId(UUID.randomUUID().toString());

        return new Api(apiRepository.create(repoApi));
    }
}
