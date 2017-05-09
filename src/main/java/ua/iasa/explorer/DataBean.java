package ua.iasa.explorer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.iasa.entity.*;
import ua.iasa.repository.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataBean {

    private final UserRepository repository;
    private final NaturalPersonRepository naturalPersonRepository;
    private final RoomRepository roomRepository;
    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        insertTestNperson();
        insertUsers();
        insertTestRoom();
        insertPost();
    }


    private void insertTestNperson() {
        NaturalPerson person = new NaturalPerson();
        person.setName("Babich Maria Pavlivna");
        person.setBirthDate("098765");
        person.setPhone("00010230");
        List<Product> products = new ArrayList<>();
        Document document = new Document(null,"10/10/10",
                new DocumentType(null, "purchase"), products, "uah");
        products.add(new Product(null, "water","8",10d,100d,document));
        products.add(new Product(null, "soap","4",5d,100d,document));

        document.setProducts(products);
        Set<Document> documents = new HashSet<>();
        documents.add(document);
        person.setDocument(documents);
        NaturalPerson p = naturalPersonRepository.save(person);

    }

    private void insertTestRoom() {
        Room room1 = roomRepository.save(new Room(null, "Store", "1", null));
        log.info("Room added", room1);
        Room room2 = roomRepository.save(new Room(null, "Luks", "1", null));
        log.info("Room added", room2);
    }

    private void insertUsers() {
        User user = repository.save(new User(null, "Dmitriy", "123", "admin"));
        log.info("User successfully added {}", user);
        User user2 = repository.save(new User(null, "Mahaon", "123", "admin"));
        log.info("User successfully added {}", user2);
    }

    private void insertPost(){
        Set<PersonOnPost> personOnPosts = new HashSet<>();
        Post post1 = postRepository.save(new Post(null, "Cleaner", 3, personOnPosts));
        log.info("Post added {}", post1);
    }
}

