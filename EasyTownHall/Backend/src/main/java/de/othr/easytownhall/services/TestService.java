package de.othr.easytownhall.services;

import de.othr.easytownhall.models.entities.TestUserModel;
import de.othr.easytownhall.repositories.TestUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.*;
@Service
public class TestService {
    //Der service ist mit einer DB verbunden. Holt die Daten, macht vllt was mit denen. Controller hat eine service instanz
    //und benutzt dann die service methoden um es an das frontend zu schicken.
    private final TestUserRepository testUserRepository;
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    public TestService(TestUserRepository testUserRepository) {
        this.testUserRepository = testUserRepository;
    }

    public TestUserModel getFirst() {
        return this.testUserRepository.findAll().get(0);
    }

    //Optionals hab ich nur in einem Video gesehen. Sonst würd ich einfach null zurückgeben und dann im Controller
    //eine 404 not found zurück geben
    public Optional<TestUserModel> getById(Long id) {
        // Nutze das bereits vorhandene `findById` von JpaRepository
        return testUserRepository.findById(id);
    }

    public List<TestUserModel> getAll() {
        return testUserRepository.findAll();
    }


    public TestUserModel addUser(TestUserModel user) {
        logger.info("Adding user: " + user.getName() + "Age: " + user.getAge());
        return testUserRepository.save(user);
    }

    public TestUserModel updateUser(TestUserModel user) {
        // save geht hier anscheinend, da JPA automatisch erkennt, ob es bereits existiert und macht dann
        // passend update oder insert
        return testUserRepository.save(user);
    }

    public boolean deleteUserById(Long id) {
        Optional<TestUserModel> userOptional = testUserRepository.findById(id);
        if (userOptional.isPresent()) {
            testUserRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
