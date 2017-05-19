package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Table
@Entity
@EqualsAndHashCode(exclude = {"products", "personal"})
@AllArgsConstructor
@NoArgsConstructor
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String date;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_doc_id")
    private DocumentType documentType;
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;
    private String currency;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Contractor contractor;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Personal personal;
}
