package ph.edu.cksc.college.advweb.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ph.edu.cksc.college.advweb.blog.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByContentContaining(@Param("content") String content, Pageable pageable);

    List<Comment> findByPostId(Long postId);
}