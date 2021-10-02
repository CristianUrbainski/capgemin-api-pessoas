package one.digitalinnovation.capgemini.api.pessoas.config;

import one.digitalinnovation.capgemini.api.pessoas.dto.PersonDTO;
import one.digitalinnovation.capgemini.api.pessoas.entity.Person;
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

        final var localDateProvider = getLocalDateProvider();
        final var converterStringToLocalDate = getConverterStringToLocalDate();
        final var converterLocalDateToString = getConverterLocalDateToString();

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(PersonDTO.class, Person.class)
                .addMappings(mapper -> mapper.skip(Person::setId))
                .addMappings(mapper -> mapper.with(localDateProvider).map(PersonDTO::getBirthDate, Person::setBirthDate))
                .addMappings(mapper -> mapper.using(converterStringToLocalDate).map(PersonDTO::getBirthDate, Person::setBirthDate));

        modelMapper.createTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

        modelMapper.addConverter(converterStringToLocalDate);
        modelMapper.addConverter(converterLocalDateToString);

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
