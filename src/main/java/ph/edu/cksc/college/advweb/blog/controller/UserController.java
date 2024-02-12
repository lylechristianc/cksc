package ph.edu.cksc.college.advweb.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.edu.cksc.college.advweb.blog.model.User;
import ph.edu.cksc.college.advweb.blog.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity <List<User>> getUsers(@RequestParam(name = "query", required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(userService.getUsers(query));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity <User> getUserById(@PathVariable long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity <User> createUser(@RequestBody User user) {
        return ResponseEntity.ok().body(this.userService.createUser(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity <User> updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok().body(this.userService.updateUser(user));
    }

    @DeleteMapping("/users/{id}")
    public HttpStatus deleteUser(@PathVariable long id) {
        this.userService.deleteUser(id);
        return HttpStatus.OK;
    }
}