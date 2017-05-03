package ua.iasa.explorer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.iasa.entity.*;
import ua.iasa.repository.NaturalPersonRepository;
import ua.iasa.repository.RoomRepository;
import ua.iasa.repository.UserRepository;

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


    @PostConstruct
    public void init() {
        insertTestNperson();
        insertUsers();
        insertTestRoom();
    }


    private void insertTestNperson() {
        NaturalPerson person = new NaturalPerson();
        person.setName("lolol");
        person.setPatronymic("ololol");
        person.setBirthDate("098765");
        Set<MovementDocument> movementDocumentSet = new HashSet<>();
        movementDocumentSet.add(new MovementDocument(null, "date",
                new DocumentType(null, "type"),
                100L,
                100D,
                new Currency(null, "baks"),
                new Room(null, "lux", "123"),
                new Product(null, "type", "125")));
        person.setDocument(movementDocumentSet);
        person.setSurname("ololo");
        person.setPhone("00010230");
        NaturalPerson p = naturalPersonRepository.save(person);

    }
    private void insertTestRoom(){
        Room room1 = roomRepository.save(new Room(null, "Store", "1") );
        log.info("Room added", room1);
        Room room2 = roomRepository.save(new Room(null, "Luks", "1") );
        log.info("Room added", room2);
    }

    private void insertUsers() {
        User user = repository.save(new User(null, "Dmitriy", "123", "admin"));
        log.info("User successfully added {}", user);
        User user2 = repository.save(new User(null, "Mahaon", "123", "admin"));
        log.info("User successfully added {}", user2);
    }
}
