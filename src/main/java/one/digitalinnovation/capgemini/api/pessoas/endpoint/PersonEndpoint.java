package one.digitalinnovation.capgemini.api.pessoas.endpoint;

import lombok.AllArgsConstructor;
import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import one.digitalinnovation.capgemini.api.pessoas.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cristian Urbainski
 * @since 29/09/2021
 */
@RestController
@RequestMapping("/api/v1/person")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonEndpoint {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Person> insert(@RequestBody Person person) {

        person = personService.save(person);

        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

}
