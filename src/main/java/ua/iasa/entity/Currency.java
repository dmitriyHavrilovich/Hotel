package ua.iasa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Currency {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;
}
