package ph.edu.cksc.college.advweb.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ph.edu.cksc.college.advweb.blog.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(Post post);

    Post updatePost(Post post);

    Page<Post> getPosts(String query, Pageable pageable);

    Post getPostById(long postId);

    void deletePost(long id);

    List<Post> findByUserId(long userId);

    Optional<Post> findById(long id);

}