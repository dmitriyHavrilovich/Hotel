package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Product {

    @Id
    private String id;
    @Column(name = "name_type")
    private String nameType;
    private String measure;
    private MovementDocument movementDocument;
}
