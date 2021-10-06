package one.digitalinnovation.capgemini.api.pessoas.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author Cristian Urbainski
 * @since 04/10/2021
 */
public abstract class JsonUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String toJson(Object object) {

        if (object == null) {

            return null;
        }

        try {

            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {

            return null;
        }
    }

}
