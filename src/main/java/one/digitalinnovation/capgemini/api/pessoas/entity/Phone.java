package one.digitalinnovation.capgemini.api.pessoas.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Cristian Urbainski
 * @since 28/09/2021
 */
@Getter
@Setter
@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhoneTypeEnum type;

    @Column(nullable = false)
    private String number;

}
