package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "contr_id")
public class JuridicalPerson extends Contractor {

    private String name;
    private String edrpou;
}
