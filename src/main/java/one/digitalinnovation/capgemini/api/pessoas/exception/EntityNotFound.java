package one.digitalinnovation.capgemini.api.pessoas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Cristian Urbainski
 * @since 01/10/2021
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public abstract class EntityNotFound extends Exception {

    private Long id;

    public EntityNotFound(String message, Long id) {

        super(message);
        this.id = id;
    }

    public Long getId() {

        return id;
    }

}
