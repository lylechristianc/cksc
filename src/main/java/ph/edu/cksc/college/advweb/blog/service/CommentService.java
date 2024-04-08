package ph.edu.cksc.college.advweb.blog.service;

import ph.edu.cksc.college.advweb.blog.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);

    Comment updateComment(Comment comment);

    Comment getCommentById(long commentId);

    void deleteComment(long id);

    List<Comment> findByPostId(long postId);
}
