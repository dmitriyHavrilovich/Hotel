package ua.iasa.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.iasa.ui.entity.ReferenceDocument;
import ua.iasa.ui.entity.ReferenceRoom;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor

public class ReferenceRoomDao {
    private final EntityManager entityManager;

    private Session getSession(){
        return (Session) entityManager.getDelegate();
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public List<ReferenceRoom> getReferencesOfRoom() {

        List<ReferenceRoom> documents =
                (List<ReferenceRoom>) getSession().createSQLQuery("SELECT\n" +
                        "  room.id,\n" +
                        "  room.room_type,\n" +
                        "  room.number,\n" +
                        "  product.name_type,\n" +
                        "  product.amount,\n" +
                       // "  product.measure,\n" +
                        "  product.price\n" +
                        "FROM room\n" +
                        "  LEFT JOIN product ON room.id = product.room_id").addScalar("id", StandardBasicTypes.LONG)
                        .addScalar("amount",StandardBasicTypes.DOUBLE)
                        .addScalar("room_type").addScalar("number").addScalar("name_type")
                       // .addScalar("measure",StandardBasicTypes.DOUBLE)
                        .addScalar("price", StandardBasicTypes.DOUBLE).setResultTransformer(new AliasToBeanResultTransformer(ReferenceRoom.class)).list();
        return documents;
    }
}
