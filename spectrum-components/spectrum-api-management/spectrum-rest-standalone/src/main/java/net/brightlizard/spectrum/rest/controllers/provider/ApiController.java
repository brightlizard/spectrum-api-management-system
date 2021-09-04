package net.brightlizard.spectrum.rest.controllers.provider;

import net.brightlizard.spectrum.repository.api.ApiRepository;
import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import net.brightlizard.spectrum.rest.model.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private ArrayList<Api> apis = new ArrayList<>();

    @Autowired
    private ApiRepository apiRepository;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Collection<Api> getApis(){
        return apis;
    }

    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Api> createApi(Api newApi) throws TechnicalException {
        apis.add(newApi);

        net.brightlizard.spectrum.repository.model.Api repoApi = new net.brightlizard.spectrum.repository.model.Api();
        repoApi.setTitle(newApi.getTitle());
        repoApi.setVersion(newApi.getVersion());
        repoApi.setDescription(newApi.getDescription());
        apiRepository.create(repoApi);
        return new ResponseEntity<>(newApi, HttpStatus.OK);
    }


}
