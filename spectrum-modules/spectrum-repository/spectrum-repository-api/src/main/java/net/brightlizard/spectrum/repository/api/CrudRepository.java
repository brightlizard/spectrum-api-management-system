package net.brightlizard.spectrum.repository.api;

import net.brightlizard.spectrum.repository.exceptions.TechnicalException;
import java.util.Optional;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
interface CrudRepository<T, ID> {

    Optional<T> findById(ID id) throws TechnicalException;

    T create(T item) throws TechnicalException;

    T update(T item) throws TechnicalException;

    void delete(ID id) throws TechnicalException;

}