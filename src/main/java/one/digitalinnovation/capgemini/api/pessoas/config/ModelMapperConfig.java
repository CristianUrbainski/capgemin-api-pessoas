package one.digitalinnovation.capgemini.api.pessoas.config;

import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Cristian Urbainski
 * @since 30/09/2021
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(String.class, LocalDate.class).setProvider(getLocalDateProvider());
        modelMapper.addConverter(getConverterStringToLocalDate());
        modelMapper.addConverter(getConverterLocalDateToString());
        return modelMapper;
    }

    private Provider<LocalDate> getLocalDateProvider() {

        return new AbstractProvider<>() {
            @Override
            protected LocalDate get() {

                return LocalDate.now();
            }
        };
    }

    private Converter<String, LocalDate> getConverterStringToLocalDate() {

        return new AbstractConverter<>() {
            @Override
            protected LocalDate convert(String source) {

                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(source, format);
                return localDate;
            }
        };
    }

    private Converter<LocalDate, String> getConverterLocalDateToString() {

        return new AbstractConverter<>() {

            @Override
            protected String convert(LocalDate source) {

                if (source == null) {
                    return null;
                }

                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return source.format(format);
            }
        };
    }

}
