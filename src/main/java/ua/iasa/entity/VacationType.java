package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "vacation_type")
@AllArgsConstructor
@NoArgsConstructor
public class VacationType implements Serializable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vacation_id")
    private Set<Vacation> vacationSet;

}
