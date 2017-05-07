package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "contractor")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "contractor_type")
@AllArgsConstructor
@NoArgsConstructor
public class Contractor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "contr_phone")
    private String phone;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "contr_id")
    private Set<Document> document;


}
