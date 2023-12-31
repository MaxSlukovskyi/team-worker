package com.teamworker.rest.admin;

import com.teamworker.dtos.MainUserInfoDto;
import com.teamworker.dtos.PositionDto;
import com.teamworker.dtos.UserDto;
import com.teamworker.models.User;
import com.teamworker.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "https://team-worker-client.herokuapp.com/")
@RequestMapping(value = "/api/v1/admin/users")
@Tag(name = "/api/v1/admin/users", description = "Контролер адміністрування користувачів")
public class UserAdminRestController {

    private final UserService userService;

    @Autowired
    public UserAdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "get/all/managers")
    @Operation(summary = "Отримати всіх менеджерів")
    public ResponseEntity<List<UserDto>> getAllManagers() {
        List<User> users = userService.getAllManagers();

        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserDto> result = users.stream().map(UserDto::fromUser).collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    @Operation(summary = "Оновити користувача")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(value = "id") Long id,
            @RequestBody UserDto userDto) {

        User user = userService.update(id, userDto.toUser());

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/update/role/{id}")
    @Operation(summary = "Змінити роль користувача")
    public ResponseEntity<UserDto> updateUserRole(
            @PathVariable(value = "id") Long id,
            @RequestBody String role) {

        User user = userService.updateRole(id, role);

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Видалити користувача")
    public ResponseEntity<UserDto> deleteUser(@PathVariable(value = "id") Long id) {

        if(userService.getById(id) == null || !userService.getById(id).getAssignedTasks().isEmpty()
        || !userService.getById(id).getCreatedTasks().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
