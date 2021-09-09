package net.brightlizard.spectrum.rest.controllers.provider;

import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import net.brightlizard.spectrum.rest.error.ErrorHandler;
import net.brightlizard.spectrum.rest.model.ApiEntity;
import net.brightlizard.spectrum.rest.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ApiService apiService;

    @Autowired
    private ErrorHandler errorHandler;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ApiEntity> getApis() throws TechnicalException {
        return apiService.listApis();
    }

    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createApi(@RequestBody ApiEntity newApi) {
        // TODO: добавить создание апи совместно со спецификацией.
        // TODO: написать тесты
        LOGGER.debug("NEW API -> {}", newApi);

        try {
            return new ResponseEntity<>(apiService.create(newApi), HttpStatus.OK);
        } catch (Exception e) {
            return errorHandler.get500ErrorResponseEntity(e);
        }
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping("/{id}")
    public ResponseEntity updateApi(@RequestBody ApiEntity api, @PathVariable String id) {
        LOGGER.debug("UPDATED API -> {}", api);

        try {
            return new ResponseEntity<>(apiService.update(id, api), HttpStatus.OK);
        } catch (Exception e) {
            return errorHandler.get500ErrorResponseEntity(e);
        }

    }


}
