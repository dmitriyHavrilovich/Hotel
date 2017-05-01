package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Data
@PrimaryKeyJoinColumn(name = "contr_id")
@Table(name = "natural_person")
public class NaturalPerson extends Contractor{

    private String name;
    private String surname;
    private String patronymic;
    @Column(name = "birth_date")
    private String birthDate;
}
