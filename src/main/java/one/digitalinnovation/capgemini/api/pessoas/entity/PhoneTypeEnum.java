package one.digitalinnovation.capgemini.api.pessoas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Cristian Urbainski
 * @since 28/09/2021
 */
@Getter
@AllArgsConstructor
public enum PhoneTypeEnum {
    HOME("Home"),
    MOBILE("Mobile"),
    COMERCIAL("Comercial");

    private final String description;
}
