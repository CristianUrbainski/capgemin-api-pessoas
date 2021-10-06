package one.digitalinnovation.capgemini.api.pessoas.utils;

import one.digitalinnovation.capgemini.api.pessoas.dto.PhoneDTO;
import one.digitalinnovation.capgemini.api.pessoas.entity.Phone;
import one.digitalinnovation.capgemini.api.pessoas.entity.PhoneTypeEnum;

/**
 * @author Cristian Urbainski
 * @since 03/10/2021
 */
public abstract class PhoneUtils {

    private static final String PHONE_NUMBER = "99991999999999";
    private static final PhoneTypeEnum PHONE_TYPE = PhoneTypeEnum.MOBILE;
    private static final long PHONE_ID = 1L;

    public static PhoneDTO createFakeDTO() {
        return PhoneDTO.builder()
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }

    public static Phone createFakeEntity() {
        return Phone.builder()
                .id(PHONE_ID)
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }

}
