package ua.iasa.explorer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.iasa.entity.User;
import ua.iasa.repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestBean {

    private final UserRepository repository;


    @PostConstruct
    public void init(){
        User user = repository.save(new User(null,"Dmitriy","123","admin"));
        log.info("User successfully added {}", user);
    }

}
