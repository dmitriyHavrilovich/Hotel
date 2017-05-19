package ua.iasa.repository;

import javafx.scene.control.Alert;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.iasa.ui.entity.ReferenceRoom;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
@RequiredArgsConstructor

public class ReferenceRoomDao {
    private final EntityManager entityManager;

    private Session getSession() {
        return (Session) entityManager.getDelegate();
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public Set<ReferenceRoom> getReferencesOfRoom() {

        List<ReferenceRoom> rooms =
                (List<ReferenceRoom>) getSession().createSQLQuery("SELECT\n" +
                        "  room.id,\n" +
                        "  room.room_type,\n" +
                        "  room.number,\n" +
                        "  room_product.name_type,\n" +
                        "  room_product.amount\n" +
                        // "  product.measure,\n" +
                        // "  room_product.price\n" +
                        "FROM room \n" +
                        "  LEFT JOIN room_product \n" +
                        " ON room.id = room_product.room_id \n")
                        .addScalar("id", StandardBasicTypes.LONG)
                        .addScalar("amount", StandardBasicTypes.DOUBLE)
                        .addScalar("room_type").addScalar("number").addScalar("name_type")
                        // .addScalar("measure",StandardBasicTypes.DOUBLE)
                        // .addScalar("price", StandardBasicTypes.DOUBLE)
                        .setResultTransformer(new AliasToBeanResultTransformer
                                (ReferenceRoom.class)).list();
        return new HashSet<>(rooms);
    }

    public void moveProduct(String sourceRoom, String targetRoom,
                            String product, Double amount) {
try{
        entityManager.createNativeQuery("SELECT move_product(:sourceRoom, :targetRoom, :product, :amount) AS text")
                .setParameter("sourceRoom", sourceRoom)
                .setParameter("targetRoom", targetRoom)
                .setParameter("product", product)
                .setParameter("amount", amount).getSingleResult();}
        catch(JDBCException e) {
            SQLException sqlException = e.getSQLException();
            if (sqlException instanceof PSQLException) {
                PSQLException psqlException = (PSQLException) sqlException;
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        psqlException.getMessage());
                alert.show();


    }}}


    @SneakyThrows
    @SuppressWarnings("unchecked")
        public Set<ReferenceRoom> ViewProduct(){
            List<ReferenceRoom> rooms =
                    (List<ReferenceRoom>)
        getSession().createSQLQuery("SELECT * FROM Product_in_room")
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("amount", StandardBasicTypes.DOUBLE)
                .addScalar("room_type").addScalar("number")
                .addScalar("name_type").setResultTransformer(new AliasToBeanResultTransformer
                (ReferenceRoom.class)).list();
        return new HashSet<>(rooms);
    }
}
