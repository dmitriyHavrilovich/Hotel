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

    public NaturalPerson(Long id, String phone, String name, Set<Document> document, String birthDate) {
        super(id, phone, name, document);
        this.birthDate = birthDate;
    }

    @Column(name = "birth_date")
    private String birthDate;
}
