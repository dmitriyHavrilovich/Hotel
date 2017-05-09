package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Data
@Entity
@Table(name = "personal")
@AllArgsConstructor
@NoArgsConstructor
public class Personal implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "personal_id")
    private Set<Document> document;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn( name = "personal_id")
    private Set<PersonOnPost> personOnPosts;
}
