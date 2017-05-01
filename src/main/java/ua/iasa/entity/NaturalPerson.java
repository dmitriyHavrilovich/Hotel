package ua.iasa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@PrimaryKeyJoinColumn(name = "contr_id")
@Table(name = "natural_person")
@NoArgsConstructor
public class NaturalPerson extends Contractor implements Serializable {

    public NaturalPerson(Long id, String phone, Set<MovementDocument> document, String name,
                         String surname, String patronymic, String birthDate) {
        super(id, phone, document);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }
    private String name;
    private String surname;
    private String patronymic;
    @Column(name = "birth_date")
    private String birthDate;
}
