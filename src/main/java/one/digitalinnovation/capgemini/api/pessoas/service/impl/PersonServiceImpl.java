package one.digitalinnovation.capgemini.api.pessoas.service.impl;

import lombok.AllArgsConstructor;
import one.digitalinnovation.capgemini.api.pessoas.dto.PersonDTO;
import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import one.digitalinnovation.capgemini.api.pessoas.exception.PersonNotFound;
import one.digitalinnovation.capgemini.api.pessoas.repository.PersonRepository;
import one.digitalinnovation.capgemini.api.pessoas.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 29/09/2021
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonServiceImpl implements PersonService {

    private final ModelMapper modelMapper;
    private final PersonRepository personRepository;

    @Override
    public Person save(PersonDTO personDTO) {

        var person = modelMapper.map(personDTO, Person.class);

        return personRepository.save(person);
    }

    @Override
    public Person update(Long id, PersonDTO personDTO) throws PersonNotFound {

        var person = getPerson(id);

        modelMapper.map(personDTO, person);

        return personRepository.save(person);
    }

    @Override
    public void delete(Long id) throws PersonNotFound {

        var person = getPerson(id);

        personRepository.delete(person);
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {

        return personRepository.findAll(pageable);
    }

    @Override
    public Optional<Person> findById(Long id) {

        return personRepository.findById(id);
    }

    private Person getPerson(@PathVariable("id") Long id) throws PersonNotFound {

        return findById(id).orElseThrow(() -> new PersonNotFound(id));
    }

}
