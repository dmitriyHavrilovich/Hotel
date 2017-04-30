package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Contractor {

    @Id
    private Long id;

    @Column(name = "contr_phone")
    private String phone;
}
