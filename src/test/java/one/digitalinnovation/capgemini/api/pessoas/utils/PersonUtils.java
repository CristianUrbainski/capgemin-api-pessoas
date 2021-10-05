package one.digitalinnovation.capgemini.api.pessoas.utils;

import one.digitalinnovation.capgemini.api.pessoas.dto.PersonDTO;
import one.digitalinnovation.capgemini.api.pessoas.entity.Person;

import java.time.LocalDate;
import java.util.Collections;

/**
 * @author Cristian Urbainski
 * @since 03/10/2021
 */
public class PersonUtils {

    private static final String FIRST_NAME = "Yuri";
    private static final String LAST_NAME = "Alberto";
    private static final String CPF_NUMBER = "94011291005";
    private static final long PERSON_ID = 1L;
    public static final LocalDate BIRTH_DATE = LocalDate.of(2010, 10, 1);

    public static PersonDTO createFakeDTO() {
        return PersonDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate("04-04-2010")
                .phones(Collections.singletonList(PhoneUtils.createFakeDTO()))
                .build();
    }

    public static Person createFakeEntity() {
        return Person.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(BIRTH_DATE)
                .phones(Collections.singletonList(PhoneUtils.createFakeEntity()))
                .build();
    }

}
