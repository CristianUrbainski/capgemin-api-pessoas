package one.digitalinnovation.capgemini.api.pessoas.endpoint;

import one.digitalinnovation.capgemini.api.pessoas.dto.PersonDTO;
import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
import one.digitalinnovation.capgemini.api.pessoas.exception.PersonNotFound;
import one.digitalinnovation.capgemini.api.pessoas.service.PersonService;
import one.digitalinnovation.capgemini.api.pessoas.utils.JsonUtils;
import one.digitalinnovation.capgemini.api.pessoas.utils.PersonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Cristian Urbainski
 * @since 04/10/2021
 */
@ExtendWith(MockitoExtension.class)
public class PersonEndpointTest {

    private static final String PERSON_API_URL_PATH = "/api/v1/person";

    private MockMvc mockMvc;

    private PersonEndpoint personEndpoint;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PersonService personService;

    @BeforeEach
    public void setUp() {

        personEndpoint = new PersonEndpoint(personService, modelMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(personEndpoint)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(((viewName, locale) -> new MappingJackson2JsonView()))
                .build();
    }

    @Test
    public void test_insert_expectedHttpStatus201() throws Exception {

        var dto = PersonUtils.createFakeDTOWithId();
        var expectedPerson = PersonUtils.createFakeEntity();

        Mockito.when(modelMapper.map(Mockito.any(Person.class), Mockito.any())).thenReturn(dto);
        Mockito.when(personService.save(Mockito.any(PersonDTO.class))).thenReturn(expectedPerson);

        var mockRequestBuilder = MockMvcRequestBuilders
                .post(PERSON_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(dto));

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(dto.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(expectedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedPerson.getLastName())))
                .andExpect(jsonPath("$.cpf", is(expectedPerson.getCpf())))
                .andExpect(jsonPath("$.birthDate", is(dto.getBirthDate())))
                .andExpect(jsonPath("$.phones", hasSize(1)));
    }

    @Test
    public void test_insert_expectedHttpStatus500() throws Exception {

        var dto = PersonUtils.createFakeDTO();
        dto.setFirstName(null);

        var mockRequestBuilder = MockMvcRequestBuilders
                .post(PERSON_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(dto));

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_update_expectedHttpStatus201() throws Exception {

        var newFirstName = "Yuri Alberto Monteiro";
        var newLastName = "da Silva";

        var dto = PersonUtils.createFakeDTOWithId();
        dto.setFirstName(newFirstName);
        dto.setLastName(newLastName);

        var expectedPerson = PersonUtils.createFakeEntity();
        expectedPerson.setFirstName(newFirstName);
        expectedPerson.setLastName(newLastName);

        Mockito.when(modelMapper.map(Mockito.any(Person.class), Mockito.any())).thenReturn(dto);
        Mockito.when(personService.update(Mockito.any(), Mockito.any(PersonDTO.class))).thenReturn(expectedPerson);

        var mockRequestBuilder = MockMvcRequestBuilders
                .put(String.format(PERSON_API_URL_PATH + "/%d", dto.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(dto));

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(expectedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedPerson.getLastName())))
                .andExpect(jsonPath("$.cpf", is(expectedPerson.getCpf())))
                .andExpect(jsonPath("$.birthDate", is(dto.getBirthDate())))
                .andExpect(jsonPath("$.phones", hasSize(1)));
    }

    @Test
    public void test_update_expectedHttpStatus500() throws Exception {

        var dto = PersonUtils.createFakeDTO();
        dto.setFirstName(null);

        var mockRequestBuilder = MockMvcRequestBuilders
                .put(String.format(PERSON_API_URL_PATH + "/%d", dto.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(dto));

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_update_expectedHttpStatus404() throws Exception {

        var dto = PersonUtils.createFakeDTOWithId();

        Mockito.when(personService.update(Mockito.anyLong(), Mockito.any(PersonDTO.class)))
                .thenThrow(new PersonNotFound(dto.getId()));

        var mockRequestBuilder = MockMvcRequestBuilders
                .put(String.format(PERSON_API_URL_PATH + "/%d", dto.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(dto));

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_deleteById_expectedHttpStatus200() throws Exception {

        var person = PersonUtils.createFakeEntity();

        var mockRequestBuilder = MockMvcRequestBuilders
                .delete(String.format(PERSON_API_URL_PATH + "/%d", person.getId()));

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_deleteById_expectedHttpStatus404() throws Exception {

        Long id = 1L;

        Mockito.doThrow(new PersonNotFound(id)).when(personService).delete(Mockito.anyLong());

        var mockRequestBuilder = MockMvcRequestBuilders
                .delete(String.format(PERSON_API_URL_PATH + "/%d", id));

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_findAll_withData() throws Exception {

        var person = PersonUtils.createFakeEntity();
        var pageable = PageRequest.of(0, 30);

        Mockito.when(personService.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(person), pageable, 1));

        var mockRequestBuilder = MockMvcRequestBuilders.get(PERSON_API_URL_PATH)
                .queryParam("page", "0")
                .queryParam("size", "30");

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.empty", is(false)))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.last", is(true)))
                .andExpect(jsonPath("$.size", is(30)))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)));
    }

    @Test
    public void test_findAll_withoutData() throws Exception {

        var pageable = PageRequest.of(0, 30);

        Mockito.when(personService.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));

        var mockRequestBuilder = MockMvcRequestBuilders.get(PERSON_API_URL_PATH)
                .queryParam("page", "0")
                .queryParam("size", "30");

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.empty", is(true)))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.last", is(true)))
                .andExpect(jsonPath("$.size", is(30)))
                .andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.totalPages", is(0)));
    }

    @Test
    public void test_findById_expectedHttpStatus200() throws Exception {

        var dto = PersonUtils.createFakeDTOWithId();
        var person = PersonUtils.createFakeEntity();

        Mockito.when(modelMapper.map(Mockito.any(Person.class), Mockito.any())).thenReturn(dto);
        Mockito.when(personService.findById(Mockito.anyLong())).thenReturn(Optional.of(person));

        var mockRequestBuilder = MockMvcRequestBuilders
                .get(String.format(PERSON_API_URL_PATH + "/%d", person.getId()));

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.cpf", is(person.getCpf())))
                .andExpect(jsonPath("$.birthDate", is(dto.getBirthDate())))
                .andExpect(jsonPath("$.phones", hasSize(1)));
    }

    @Test
    public void test_findById_expectedHttpStatus404() throws Exception {

        var dto = PersonUtils.createFakeDTO();
        var person = PersonUtils.createFakeEntity();

        Mockito.when(personService.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        var mockRequestBuilder = MockMvcRequestBuilders
                .get(String.format(PERSON_API_URL_PATH + "/%d", person.getId()));

        mockMvc.perform(mockRequestBuilder)
                .andExpect(status().isNotFound());
    }

}
