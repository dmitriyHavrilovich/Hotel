package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Mahaon on 08.05.2017.
 */
@Data
@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int quantity;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name = "post_id")
    private Set<PersonOnPost> personOnPosts;
}
