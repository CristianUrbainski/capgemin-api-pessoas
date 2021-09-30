package one.digitalinnovation.capgemini.api.pessoas.repository;

import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Cristian Urbainski
 * @since 29/09/2021
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
