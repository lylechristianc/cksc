package ph.edu.cksc.college.advweb.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ph.edu.cksc.college.advweb.blog.model.Post;
import ph.edu.cksc.college.advweb.blog.model.User;
import ph.edu.cksc.college.advweb.blog.model.View;
import ph.edu.cksc.college.advweb.blog.service.AuthenticatedUserService;
import ph.edu.cksc.college.advweb.blog.service.PostService;
import ph.edu.cksc.college.advweb.blog.service.UserService;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @JsonView(View.Summary.class)
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts(
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "dir", required = false, defaultValue = "ASC") String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(postService.getPosts(query, pageable).getContent());
    }

    @JsonView(View.Summary.class)
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable long id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @PostMapping("/posts")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Post> createPost(@RequestBody Post post) throws Exception {
        long userId = post.getUser().getId();
        String username =  SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByName(username);
        user.ifPresent(post::setUser);
        if (user.isEmpty())
            throw new Exception(String.format("User with id %d is not found", userId));
        return ResponseEntity.ok().body(this.postService.createPost(post));
    }

    @PutMapping("/posts/{id}")
    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#id)")
    public ResponseEntity<Post> updatePost(@PathVariable long id, @RequestBody Post post) {
        post.setId(id);
        return ResponseEntity.ok().body(this.postService.updatePost(post));
    }

    @DeleteMapping("/posts/{id}")
    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#id)")
    public HttpStatus deletePost(@PathVariable long id) {
        this.postService.deletePost(id);
        return HttpStatus.OK;
    }
}