package net.brightlizard.spectrum.repository.api;

import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import java.util.List;
import java.util.Optional;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public interface ContextPathRepository {

    List<String> findContextPaths(String v) throws TechnicalException;
    Optional<String> findContextPathById(String id) throws TechnicalException;

}
