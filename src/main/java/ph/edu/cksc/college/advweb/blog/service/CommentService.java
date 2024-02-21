package ph.edu.cksc.college.advweb.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ph.edu.cksc.college.advweb.blog.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);

    Comment updateComment(Comment comment);

    Page<Comment> getComments(String query, Pageable pageable);

    Comment getCommentById(long commentId);

    void deleteComment(long id);

    List<Comment> findByPostId(long postId);
}