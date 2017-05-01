package ua.iasa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "contractor")
public class Contractor implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "contr_phone")
    private String phone;
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="contr_id")
    private Set<Document> document;


}
