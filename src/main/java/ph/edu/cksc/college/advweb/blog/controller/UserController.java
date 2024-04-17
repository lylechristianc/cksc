package ph.edu.cksc.college.advweb.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ph.edu.cksc.college.advweb.blog.model.Post;
import ph.edu.cksc.college.advweb.blog.model.User;
import ph.edu.cksc.college.advweb.blog.model.View;
import ph.edu.cksc.college.advweb.blog.service.AuthenticatedUserService;
import ph.edu.cksc.college.advweb.blog.service.PostService;
import ph.edu.cksc.college.advweb.blog.service.UserService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers(@RequestParam(name = "query", required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(userService.getUsers(query));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#id)")
    public ResponseEntity <User> getUserById(@PathVariable long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <User> createUser(@RequestBody User user) {
        return ResponseEntity.ok().body(this.userService.createUser(user));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#id)")
    public ResponseEntity <User> updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok().body(this.userService.updateUser(user));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpStatus deleteUser(@PathVariable long id) {
        this.userService.deleteUser(id);
        return HttpStatus.OK;
    }

    @Autowired
    private PostService postService;

    @JsonView(View.Summary.class)
    @GetMapping("/users/{userId}/posts")
    public List<Post> getPostsByUser(@PathVariable(value = "userId") Long userId) {
        return postService.findByUserId(userId);
    }
}