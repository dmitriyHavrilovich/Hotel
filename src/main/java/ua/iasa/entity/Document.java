package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name = "document")
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "document_type")
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String date;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_doc_id")
    private DocumentType documentType;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private List<Product> products;
    private String currency;
}
