package ph.edu.cksc.college.advweb.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.edu.cksc.college.advweb.blog.exception.ResourceNotFoundException;
import ph.edu.cksc.college.advweb.blog.model.Comment;
import ph.edu.cksc.college.advweb.blog.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        Optional<Comment> commentDb = this.commentRepository.findById(comment.getId());

        if (commentDb.isPresent()) {
            Comment commentUpdate = commentDb.get();
            commentUpdate.setId(comment.getId());
            commentUpdate.setContent(comment.getContent());
            commentUpdate.setPublished(comment.isPublished());
            commentRepository.save(commentUpdate);
            return commentUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + comment.getId());
        }
    }

    @Override
    public Comment getCommentById(long commentId) {
        Optional<Comment> commentDb = this.commentRepository.findById(commentId);

        if (commentDb.isPresent()) {
            return commentDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + commentId);
        }
    }

    @Override
    public void deleteComment(long commentId) {
        Optional<Comment> commentDb = this.commentRepository.findById(commentId);

        if (commentDb.isPresent()) {
            this.commentRepository.delete(commentDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + commentId);
        }
    }

    @Override
    public List<Comment> findByPostId(long postId) {
        return commentRepository.findByPostId(postId);
    }
}