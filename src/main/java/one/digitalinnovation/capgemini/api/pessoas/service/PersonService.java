package one.digitalinnovation.capgemini.api.pessoas.service;

import one.digitalinnovation.capgemini.api.pessoas.dto.PersonDTO;
import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import one.digitalinnovation.capgemini.api.pessoas.exception.PersonNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 29/09/2021
 */
public interface PersonService {

    Person save(PersonDTO personDTO);

    Person update(Long id, PersonDTO personDTO) throws PersonNotFound;

    void delete(Long id) throws PersonNotFound;

    Page<Person> findAll(Pageable pageable);

    Optional<Person> findById(Long id);
}
