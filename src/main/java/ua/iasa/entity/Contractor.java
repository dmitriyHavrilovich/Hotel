package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "contractor")
public abstract class Contractor {

    @Id
    private Long id;
    @Column(name = "contr_phone")
    private String phone;
    private Document document;


}
