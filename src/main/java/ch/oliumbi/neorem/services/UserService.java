package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.User;
import ch.oliumbi.neorem.entities.UserRole;
import ch.oliumbi.neorem.entities.UserRoleId;
import ch.oliumbi.neorem.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void tempInit() {
        UUID id = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");

        User user = new User(id, "admin", "$2a$10$znlTKuuW1vzb0KZfVyh5OOZoB46FeENzBcU2/8qwJ6kiuC3fCCtXK", Set.of(new UserRole(new UserRoleId(id, "TEST"))));
        userRepository.save(user);
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
}
