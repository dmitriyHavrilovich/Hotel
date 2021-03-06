package ua.iasa.explorer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Service;
import ua.iasa.entity.*;
import ua.iasa.repository.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataBean implements SmartInitializingSingleton {

    private final NaturalPersonRepository naturalPersonRepository;
    private final RoomRepository roomRepository;

    private final EntityManager em;
    private final PersonalRepository personalRepository;
    private final ReferenceDocumentsDao documentsDao;

    @PostConstruct
    public void init() {
        insertTestNperson();
        insertTestRoom();


    }

    private void insertTestNperson() {
        NaturalPerson person = new NaturalPerson();
        Personal personal = new Personal();
        personal.setNamep("Babich MP");
        person.setName("Babich Maria Pavlivna");
        person.setBirthDate("098765");
        person.setPhone("00010230");
        List<Product> products = new ArrayList<>();
        Document document = new Document(null, "10/10/10",
                new DocumentType(null, "purchase"), products, "uah", person, personal);
        products.add(new Product(null, "water", "8", 10d, 100d, document));
        products.add(new Product(null, "soap", "4", 5d, 100d, document));

        document.setProducts(products);
        Set<Document> documents = new HashSet<>();
        documents.add(document);
        person.setDocument(documents);
        personal.setDocument(documents);
        NaturalPerson p = naturalPersonRepository.save(person);
        Personal personal1=personalRepository.save(personal);
    }

    private void insertTestRoom() {
        Room room1 = roomRepository.save(new Room(null, "Store", "1", null));
        log.info("Room added", room1);
        Room room2 = roomRepository.save(new Room(null, "Luks", "2", null));
        log.info("Room added", room2);
        Room room3 = roomRepository.save(new Room(null, "Poluluks", "3", null));
        log.info("Room added", room3);
        Room room4 = roomRepository.save(new Room(null, "Econom", "4", null));
        log.info("Room added", room4);
        Room room5 = roomRepository.save(new Room(null, "Bisness_class", "5", null));
        log.info("Room added", room5);
    }




    @Override
    @SneakyThrows
    @Transactional
    public void afterSingletonsInstantiated() {
        String sql = FileUtils.readFileToString(new File("./src/main/resources/sql/script.sql"));
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

}

