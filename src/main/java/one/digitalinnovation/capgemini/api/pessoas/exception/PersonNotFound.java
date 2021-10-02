package one.digitalinnovation.capgemini.api.pessoas.exception;

/**
 * @author Cristian Urbainski
 * @since 01/10/2021
 */
public class PersonNotFound extends EntityNotFound {

    public PersonNotFound(Long id) {
        super(String.format("Pessoa com id = %d não encontrada", id), id);
    }

}
