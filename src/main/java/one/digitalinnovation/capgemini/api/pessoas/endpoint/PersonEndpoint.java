package one.digitalinnovation.capgemini.api.pessoas.endpoint;

import lombok.AllArgsConstructor;
import one.digitalinnovation.capgemini.api.pessoas.dto.PersonDTO;
import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import one.digitalinnovation.capgemini.api.pessoas.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Cristian Urbainski
 * @since 29/09/2021
 */
@RestController
@RequestMapping("/api/v1/person")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonEndpoint {

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PersonDTO> insert(@RequestBody @Valid PersonDTO personDTO) {

        Person person = modelMapper.map(personDTO, Person.class);

        person = personService.save(person);

        PersonDTO result = modelMapper.map(person, PersonDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
