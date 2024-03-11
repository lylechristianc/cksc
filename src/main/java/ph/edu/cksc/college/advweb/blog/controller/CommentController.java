package ph.edu.cksc.college.advweb.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.edu.cksc.college.advweb.blog.model.Comment;
import ph.edu.cksc.college.advweb.blog.model.Post;
import ph.edu.cksc.college.advweb.blog.model.User;
import ph.edu.cksc.college.advweb.blog.model.View;
import ph.edu.cksc.college.advweb.blog.service.CommentService;
import ph.edu.cksc.college.advweb.blog.service.PostService;
import ph.edu.cksc.college.advweb.blog.service.UserService;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @JsonView(View.Summary.class)
    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentById ( @PathVariable long id){
        return ResponseEntity.ok().body(commentService.getCommentById(id));
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) throws Exception {
        long userId = comment.getUser().getId();
        Optional<User> user = userService.findById(userId);
        user.ifPresent(comment::setUser);
        if (user.isEmpty())
            throw new Exception(String.format("User with id %d is not found", userId));
        long postId = comment.getPost().getId();
        Optional<Post> post = postService.findById(postId);
        post.ifPresent(comment::setPost);
        if (post.isEmpty())
            throw new Exception(String.format("Post with id %d is not found", postId));
        return ResponseEntity.ok().body(this.commentService.createComment(comment));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment ( @PathVariable long id, @RequestBody Comment comment){
        return ResponseEntity.ok().body(this.commentService.updateComment(comment));
    }

    @DeleteMapping("/comments/{id}")
    public HttpStatus deleteComment ( @PathVariable long id){
        this.commentService.deleteComment(id);
        return HttpStatus.OK;
    }
}