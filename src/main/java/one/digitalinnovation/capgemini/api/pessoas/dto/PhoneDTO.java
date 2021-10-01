package one.digitalinnovation.capgemini.api.pessoas.dto;

import lombok.Getter;
import lombok.Setter;
import one.digitalinnovation.capgemini.api.pessoas.entity.PhoneTypeEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Cristian Urbainski
 * @since 30/09/2021
 */
@Getter
@Setter
public class PhoneDTO {

    private Long id;

    @NotNull
    private PhoneTypeEnum type;

    @NotEmpty
    @Size(min = 13, max = 14)
    private String number;

}