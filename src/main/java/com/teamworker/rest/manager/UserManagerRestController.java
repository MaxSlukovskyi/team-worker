package com.teamworker.rest.manager;

import com.teamworker.dtos.UserDto;
import com.teamworker.models.User;
import com.teamworker.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "/api/v1/manager/users")
@Tag(name = "/api/v1/manager/users", description = "Контролер для керування користувачами")
public class UserManagerRestController {

    private final UserService userService;

    @Autowired
    public UserManagerRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "get/all")
    @Operation(summary = "Отримати всіх користувачів")
    public ResponseEntity<List<UserDto>> getAll() {
        List<User> users = userService.getAll();

        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserDto> result = users.stream().map(UserDto::fromUser).collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
