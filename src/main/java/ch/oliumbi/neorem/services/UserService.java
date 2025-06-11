package ch.oliumbi.neorem.services;

import ch.oliumbi.neorem.entities.User;
import ch.oliumbi.neorem.entities.UserRole;
import ch.oliumbi.neorem.entities.UserRoleId;
import ch.oliumbi.neorem.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void tempInit() {
        UUID id = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");

        User user = new User(id, "admin", "$2a$10$znlTKuuW1vzb0KZfVyh5OOZoB46FeENzBcU2/8qwJ6kiuC3fCCtXK", Set.of(new UserRole(new UserRoleId(id, "TEST"))));
        userRepository.save(user);
    }

    public User byId(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<User> all() {
        return userRepository.findAll();
    }

    public void create(String name, String password, List<String> roles) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));

        for (String role : roles) {
            UserRole userRole = new UserRole(new UserRoleId(user.getId(), role));
            user.getRoles().add(userRole);
        }

        userRepository.save(user);
    }

    public void update(UUID id, String name, List<String> roles) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setName(name);

        user.getRoles().clear();
        for (String role : roles) {
            UserRole userRole = new UserRole(new UserRoleId(user.getId(), role));
            user.getRoles().add(userRole);
        }

        userRepository.save(user);
    }

    public void updatePassword(UUID id, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
