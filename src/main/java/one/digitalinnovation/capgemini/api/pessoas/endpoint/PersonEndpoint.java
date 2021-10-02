package one.digitalinnovation.capgemini.api.pessoas.endpoint;

import lombok.AllArgsConstructor;
import one.digitalinnovation.capgemini.api.pessoas.dto.PersonDTO;
import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import one.digitalinnovation.capgemini.api.pessoas.exception.PersonNotFound;
import one.digitalinnovation.capgemini.api.pessoas.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

        var person = modelMapper.map(personDTO, Person.class);

        person = personService.save(person);

        var result = modelMapper.map(person, PersonDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable("id") Long id, @RequestBody @Valid PersonDTO dto) throws PersonNotFound {

        var person = getPerson(id);

        modelMapper.map(dto, person);

        person = personService.save(person);

        var dtoResponse = modelMapper.map(person, PersonDTO.class);

        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) throws PersonNotFound {

        Person person = getPerson(id);

        personService.delete(person);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Page<PersonDTO>> findAll(@PageableDefault(page = 0, size = 20) Pageable pageable) {

        var page = personService.findAll(pageable);

        var listDtoResponse = page
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .stream()
                .collect(Collectors.toList());

        var pageResponse = new PageImpl<>(listDtoResponse, pageable, page.getTotalElements());

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Long id) throws PersonNotFound {

        Person person = getPerson(id);

        var dto = modelMapper.map(person, PersonDTO.class);

        return ResponseEntity.ok(dto);
    }

    private Person getPerson(@PathVariable("id") Long id) throws PersonNotFound {

        return personService.findById(id).orElseThrow(() -> new PersonNotFound(id));
    }

}
