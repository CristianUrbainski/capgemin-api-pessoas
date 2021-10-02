package one.digitalinnovation.capgemini.api.pessoas.service;

import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 29/09/2021
 */
public interface PersonService {

    Person save(Person person);

    void delete(Person person);

    Page<Person> findAll(Pageable pageable);

    Optional<Person> findById(Long id);
}
