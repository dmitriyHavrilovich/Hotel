package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;
}
