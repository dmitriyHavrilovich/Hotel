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
@Table(name = "person_on_post")
@AllArgsConstructor
@NoArgsConstructor
public class PersonOnPost implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String beginDate;
    @Column
    private String endDate;
    private Double salary;
   // @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   // private Personal personal;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Post post;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vacation_id")
    private Set<Vacation> vacations;
}
