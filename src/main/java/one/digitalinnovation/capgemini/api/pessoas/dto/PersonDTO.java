package one.digitalinnovation.capgemini.api.pessoas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import one.digitalinnovation.capgemini.api.pessoas.entity.Phone;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Cristian Urbainski
 * @since 30/09/2021
 */
@Getter
@Setter
@Builder
public class PersonDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String lastName;

    @NotEmpty
    @CPF
    private String cpf;

    private String birthDate;

    @Valid
    @NotEmpty
    private List<PhoneDTO> phones;

}
