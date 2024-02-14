package ph.edu.cksc.college.advweb.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.edu.cksc.college.advweb.blog.model.Post;
import ph.edu.cksc.college.advweb.blog.model.User;
import ph.edu.cksc.college.advweb.blog.model.View;
import ph.edu.cksc.college.advweb.blog.repository.UserRepository;
import ph.edu.cksc.college.advweb.blog.service.PostService;
import ph.edu.cksc.college.advweb.blog.service.UserService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @JsonView(View.Summary.class)
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts(@RequestParam(name = "query", required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(postService.getPosts(query));
    }

    @JsonView(View.Summary.class)
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable long id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Optional<User> user = userService.findById(post.getUser().getId());
        user.ifPresent(post::setUser);
        return ResponseEntity.ok().body(this.postService.createPost(post));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id, @RequestBody Post post) {
        post.setId(id);
        return ResponseEntity.ok().body(this.postService.updatePost(post));
    }

    @DeleteMapping("/posts/{id}")
    public HttpStatus deletePost(@PathVariable long id) {
        this.postService.deletePost(id);
        return HttpStatus.OK;
    }
}