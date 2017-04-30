package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class JuridicalPerson extends Contractor {

    private String name;
    private String edrpou;
}
