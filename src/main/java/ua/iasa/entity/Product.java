package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "product")
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
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "document_id")
    private Document document;

}
