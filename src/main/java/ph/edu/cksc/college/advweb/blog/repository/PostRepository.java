package ph.edu.cksc.college.advweb.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ph.edu.cksc.college.advweb.blog.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE " +
            "p.title LIKE CONCAT('%',:query, '%')" +
            "Or p.content LIKE CONCAT('%', :query, '%')")
    Page<Post> searchPosts(String query,Pageable pageable);

    Page<Post> findByTitleContaining(@Param("title") String title, Pageable pageable);

    Page<Post> findByTitleContainingOrContentContaining(@Param("title") String title, @Param("content") String content, Pageable pageable);

    List<Post> findByUserId(Long userId);
}
