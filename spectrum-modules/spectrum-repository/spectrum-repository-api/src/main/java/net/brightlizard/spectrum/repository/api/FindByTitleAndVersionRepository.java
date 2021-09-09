package net.brightlizard.spectrum.repository.api;

import net.brightlizard.spectrum.repository.exceptions.TechnicalException;

import java.util.List;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public interface FindByTitleAndVersionRepository<T, N, V> {

    List<T> findByTitleAndVersion(N title, V version) throws TechnicalException;

}
