package one.digitalinnovation.capgemini.api.pessoas.service;

import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Cristian Urbainski
 * @since 29/09/2021
 */
public interface PersonService {

    Person save(Person person);

    Page<Person> findAll(Pageable pageable);
}
