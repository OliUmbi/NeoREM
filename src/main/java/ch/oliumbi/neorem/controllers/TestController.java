package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.User;
import ch.oliumbi.neorem.entities.UserRole;
import ch.oliumbi.neorem.entities.UserRoleId;
import ch.oliumbi.neorem.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("test")
public class TestController {

    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @GetMapping()
    public String all() {

        UUID userId = UUID.randomUUID();

        User user = new User(userId, "oliumbi", "1234", Set.of(new UserRole(new UserRoleId(userId, "SETTINGS"))));

        userRepository.save(user);

        Optional<User> byId = userRepository.findById(userId);

        System.out.println(byId.get().getRoles().size());

        byId.get().getRoles().removeIf(userRole -> userRole.getId().getRole().equals("SETTINGS"));

        byId = userRepository.findById(userId);

        System.out.println(byId.get().getRoles().size());

        return "Hello World";
    }
}
