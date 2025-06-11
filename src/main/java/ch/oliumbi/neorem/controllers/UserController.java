package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.entities.User;
import ch.oliumbi.neorem.services.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured("TEST")
    @GetMapping
    public List<User> all() {
        return userService.all();
    }

    @Secured("")
    @GetMapping("{id}")
    public User byId(@RequestParam UUID id) {
        return userService.byId(id);
    }

    @Secured("TEST")
    @PostMapping
    public void create(@RequestBody CreateRequest createRequest) {
        userService.create(createRequest.name, createRequest.password, createRequest.roles);
    }

    @Secured("TEST")
    @PutMapping("{id}")
    public void update(@RequestParam UUID id, @RequestBody UpdateRequest updateRequest) {
        userService.update(id, updateRequest.name, updateRequest.roles);
    }

    @Secured("TEST")
    @PutMapping("{id}/password")
    public void updatePassword(@RequestParam UUID id, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        userService.updatePassword(id, updatePasswordRequest.password);
    }

    @Secured("TEST")
    @DeleteMapping("{id}")
    public void delete(@RequestParam UUID id) {
        userService.delete(id);
    }

    @Data
    public static class CreateRequest {

        @NotNull
        private String name;

        @NotNull
        private String password;

        @NotNull
        private List<String> roles;
    }

    @Data
    public static class UpdateRequest {

        @NotNull
        private String name;

        @NotNull
        private List<String> roles;
    }

    @Data
    public static class UpdatePasswordRequest {

        @NotNull
        private String password;
    }
}
