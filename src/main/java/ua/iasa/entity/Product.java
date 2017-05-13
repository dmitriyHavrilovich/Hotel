package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table
@EqualsAndHashCode(exclude = "document")
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name_type")
    private String nameType;
    private String measure;
    private Double amount;
    private Double price;
    @ManyToOne(cascade = CascadeType.ALL)
    private Document document;

}
