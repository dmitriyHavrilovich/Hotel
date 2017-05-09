package ua.iasa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class JuridicalPerson extends Contractor {
    public JuridicalPerson(Long id, String phone, String name, Set<Document> document, String edrpou) {
        super(id, phone, name, document);
        this.edrpou = edrpou;
    }

    private String edrpou;
}
