package net.brightlizard.spectrum.rest.controllers.provider;

import net.brightlizard.spectrum.repository.api.ApiRepository;
import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import net.brightlizard.spectrum.rest.error.ErrorHandler;
import net.brightlizard.spectrum.rest.model.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private ErrorHandler errorHandler;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<net.brightlizard.spectrum.repository.model.Api> getApis() throws TechnicalException {
        Optional<List<net.brightlizard.spectrum.repository.model.Api>> apis = apiRepository.findAll();
        return apis.orElse(new ArrayList<>());
    }

    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createApi(@RequestBody Api newApi) {
        LOGGER.debug("NEW API -> {}", newApi);

        try {
            net.brightlizard.spectrum.repository.model.Api repoApi = new net.brightlizard.spectrum.repository.model.Api();
            repoApi.setTitle(newApi.getTitle());
            repoApi.setVersion(newApi.getVersion());
            repoApi.setDescription(newApi.getDescription());
            repoApi.setSpecId(UUID.randomUUID().toString());
            return new ResponseEntity<>(apiRepository.create(repoApi), HttpStatus.OK);
        } catch (Exception e) {
            return errorHandler.get500ErrorResponseEntity(e);
        }
    }


}
