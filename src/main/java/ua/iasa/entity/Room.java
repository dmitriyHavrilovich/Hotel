package ua.iasa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "room")
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "room_type")
    private String roomType;
    @Column(name = "number")
    private String roomNumber;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private List<Product> products;
}
