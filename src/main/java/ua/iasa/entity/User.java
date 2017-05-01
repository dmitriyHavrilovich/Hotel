package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String password;
    private String group;
}
