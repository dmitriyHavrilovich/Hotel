package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mahaon on 08.05.2017.
 */
@Data
@Entity
@Table(name = "vacation")
@AllArgsConstructor
@NoArgsConstructor
public class Vacation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column
    private String begindate;
    @Column
    private String enddate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "vacation_id")
    private VacationType vacationType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private PersonOnPost personOnPost;
}
