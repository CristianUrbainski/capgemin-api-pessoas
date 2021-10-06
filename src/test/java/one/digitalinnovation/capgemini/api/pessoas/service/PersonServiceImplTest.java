package one.digitalinnovation.capgemini.api.pessoas.service;

import one.digitalinnovation.capgemini.api.pessoas.dto.PersonDTO;
import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import one.digitalinnovation.capgemini.api.pessoas.exception.PersonNotFound;
import one.digitalinnovation.capgemini.api.pessoas.repository.PersonRepository;
import one.digitalinnovation.capgemini.api.pessoas.service.impl.PersonServiceImpl;
import one.digitalinnovation.capgemini.api.pessoas.utils.PersonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 03/10/2021
 */
@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void test_save_success() {

        var expectedPerson = PersonUtils.createFakeEntity();

        Mockito.when(modelMapper.map(Mockito.any(PersonDTO.class), Mockito.any())).thenReturn(expectedPerson);
        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(expectedPerson);

        Person receivedPerson = personService.save(PersonUtils.createFakeDTO());

        Assertions.assertEquals(expectedPerson, receivedPerson);
        Assertions.assertEquals(expectedPerson.getId(), receivedPerson.getId());
    }

    @Test
    public void test_update_success() throws PersonNotFound{

        var id = 1L;
        var newFirstName = "Yuri Alberto Monteiro";
        var newLastName = "da Silva";

        var originalPerson = PersonUtils.createFakeEntity();

        var expectedPerson = PersonUtils.createFakeEntity();
        expectedPerson.setFirstName(newFirstName);
        expectedPerson.setLastName(newLastName);

        Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(originalPerson));
        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(expectedPerson);

        var dto = PersonUtils.createFakeDTO();
        dto.setFirstName(newFirstName);
        dto.setLastName(newLastName);

        Person receivedPerson = personService.update(id, dto);

        Assertions.assertEquals(expectedPerson, receivedPerson);
        Assertions.assertEquals(newFirstName, receivedPerson.getFirstName());
        Assertions.assertEquals(newLastName, receivedPerson.getLastName());
    }

    @Test
    public void test_update_expectedPersonNotFound() throws PersonNotFound {

        var id = 1L;

        Mockito.when(personRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFound.class, () -> personService.update(id, PersonUtils.createFakeDTO()));
    }

    @Test
    public void test_delete_success() throws PersonNotFound {

        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(PersonUtils.createFakeEntity()));

        personService.delete(1L);
    }

    @Test
    public void test_delete_expectedPersonNotFound() {

        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFound.class, () -> personService.delete(1L));
    }

    @Test
    public void test_findAll_returnedDice() {

        var totalElements = 1L;
        var expectedPageable = PageRequest.of(0, 20);
        var expectedList = Collections.singletonList(PersonUtils.createFakeEntity());
        var expectedPage = new PageImpl<>(expectedList, expectedPageable, totalElements);

        Mockito.when(personRepository.findAll(Mockito.any(Pageable.class))).thenReturn(expectedPage);

        var receivedPage = personService.findAll(PageRequest.of(0, 20));

        Assertions.assertNotNull(receivedPage);
        Assertions.assertNotNull(receivedPage.getContent());
        Assertions.assertEquals(receivedPage.getContent().size(), expectedList.size());
        Assertions.assertEquals(receivedPage.getTotalElements(), totalElements);
    }

    @Test
    public void test_findAll_notReturnedDice() {

        var totalElements = 1L;
        var expectedPageable = PageRequest.of(0, 20);
        var expectedList = new ArrayList<Person>();
        var expectedPage = new PageImpl<>(expectedList, expectedPageable, totalElements);

        Mockito.when(personRepository.findAll(Mockito.any(Pageable.class))).thenReturn(expectedPage);

        var receivedPage = personService.findAll(PageRequest.of(0, 20));

        Assertions.assertNotNull(receivedPage);
        Assertions.assertNotNull(receivedPage.getContent());
        Assertions.assertEquals(receivedPage.getContent().size(), expectedList.size());
        Assertions.assertEquals(receivedPage.getTotalElements(), totalElements);
    }

    @Test
    public void test_findById_sucess() {

        var id = 1L;

        var expectedPerson = PersonUtils.createFakeEntity();

        Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(expectedPerson));

        var optional = personService.findById(id);

        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(expectedPerson, optional.get());
        Assertions.assertEquals(id, optional.get().getId());
    }

    @Test
    public void test_findById_notFound() {

        var id = 1L;

        Mockito.when(personRepository.findById(id)).thenReturn(Optional.empty());

        var optional = personService.findById(id);

        Assertions.assertTrue(optional.isEmpty());
    }

}
