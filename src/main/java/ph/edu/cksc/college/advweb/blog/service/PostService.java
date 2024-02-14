package ph.edu.cksc.college.advweb.blog.service;

import ph.edu.cksc.college.advweb.blog.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);

    Post updatePost(Post post);

    List<Post> getPosts(String query);

    Post getPostById(long postId);

    void deletePost(long id);

    List<Post> findByUserId(Long userId);

}