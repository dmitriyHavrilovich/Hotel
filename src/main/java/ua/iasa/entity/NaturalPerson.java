package ua.iasa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class NaturalPerson extends Contractor {

    public NaturalPerson(Long id, String phone, Set<Document> document, String name,
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
