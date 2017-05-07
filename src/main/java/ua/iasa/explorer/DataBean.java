package ua.iasa.explorer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.iasa.entity.*;
import ua.iasa.repository.*;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataBean {

    private final UserRepository repository;
    private final NaturalPersonRepository naturalPersonRepository;
    private final RoomRepository roomRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;

    @PostConstruct
    public void init() {
        insertTestNperson();
        insertUsers();
        insertTestRoom();
        insertCurrency();
        insertProduct();
    }


    private void insertTestNperson() {
        NaturalPerson person = new NaturalPerson();
        person.setName("lolol");
        person.setPatronymic("ololol");
        person.setBirthDate("098765");
        Set<Product> products = new HashSet<>();
        Set<Document> DocumentSet = new HashSet<>();
        Set<MovementDocument> movementDocumentSet = new HashSet<>();
        Document document = new Document(null, "date",
                new DocumentType(null, "type"),
                movementDocumentSet);
        //products.add(new Product(null, "type", "125", document));
        //document.setProducts(products);
        //MovementDocument savedDocument = movementDocumentRepository.save(document);
        //log.info("this is document {}" , savedDocument);
        DocumentSet.add(document);
        person.setDocument(DocumentSet);
        person.setSurname("ololo");
        person.setPhone("00010230");
        NaturalPerson p =naturalPersonRepository.save(person);

    }

    private void insertTestRoom() {
        Room room1 = roomRepository.save(new Room(null, "Store", "1"));
        log.info("Room added", room1);
        Room room2 = roomRepository.save(new Room(null, "Luks", "1"));
        log.info("Room added", room2);
    }

    private void insertUsers() {
        User user = repository.save(new User(null, "Dmitriy", "123", "admin"));
        log.info("User successfully added {}", user);
        User user2 = repository.save(new User(null, "Mahaon", "123", "admin"));
        log.info("User successfully added {}", user2);
    }
    private void insertCurrency(){
        Currency currency1 = currencyRepository.save(new Currency(null, "EUR"));
        log.info("Currency added {}", currency1);
        Currency currency2 = currencyRepository.save(new Currency(null, "UAH"));
        log.info("Currency added {}", currency2);
        Currency currency3 = currencyRepository.save(new Currency(null, "DOLLAR"));
        log.info("Currency added {}", currency3);
    }

    private void insertProduct(){
        Set<MovementDocument> movementDocumentSet = new HashSet<>();
        Product product1 = productRepository.save(new Product(null, "Linens", "unity", movementDocumentSet));
        log.info("Product added {}", product1);
    }

}

