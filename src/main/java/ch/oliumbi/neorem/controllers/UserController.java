package ch.oliumbi.neorem.controllers;

import ch.oliumbi.neorem.services.SettingService;
import ch.oliumbi.neorem.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // todo get user

    // todo get all users

    // todo create user

    // todo update user name, roles

    // todo update user password

    // todo delete user
}
