package net.brightlizard.spectrum.repository.api;

import net.brightlizard.spectrum.repository.model.Api;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public interface ApiRepository extends CrudRepository<Api, String>, FindByTitleAndVersionRepository<Api, String, String>, ContextPathRepository {

}
