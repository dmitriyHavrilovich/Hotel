package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    @Column(name = "name_type")
    private String nameType;
    private String measure;
}
