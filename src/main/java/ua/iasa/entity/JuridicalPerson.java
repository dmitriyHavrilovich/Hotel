package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "contr_id")
@Table(name = "juridical_person")
public class JuridicalPerson extends Contractor {

    private String name;
    private String edrpou;
}
