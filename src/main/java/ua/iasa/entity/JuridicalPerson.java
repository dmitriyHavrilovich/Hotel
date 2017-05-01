package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "contr_id")
@Table(name = "juridical_person")
public class JuridicalPerson extends Contractor implements Serializable{

    public JuridicalPerson(Long id, String phone, Set<MovementDocument> document,
                           String name, String edrpou) {
        super(id, phone, document);
        this.name = name;
        this.edrpou = edrpou;
    }

    private String name;
    private String edrpou;
}
