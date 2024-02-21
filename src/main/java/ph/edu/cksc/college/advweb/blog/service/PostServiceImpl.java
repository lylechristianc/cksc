package ph.edu.cksc.college.advweb.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.edu.cksc.college.advweb.blog.exception.ResourceNotFoundException;
import ph.edu.cksc.college.advweb.blog.model.Post;
import ph.edu.cksc.college.advweb.blog.repository.PostRepository;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        Optional<Post> postDb = this.postRepository.findById(post.getId());

        if (postDb.isPresent()) {
            Post postUpdate = postDb.get();
            postUpdate.setId(post.getId());
            postUpdate.setTitle(post.getTitle());
            postUpdate.setContent(post.getContent());
            postUpdate.setPublished(post.isPublished());
            postRepository.save(postUpdate);
            return postUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + post.getId());
        }
    }

    @Override
    public Page<Post> getPosts(String query, Pageable pageable) {
        //return postRepository.searchPosts(query, pageable);
        return postRepository.findByTitleContainingOrContentContaining(query, query, pageable);
    }

    @Override
    public Post getPostById(long postId) {

        Optional<Post> postDb = this.postRepository.findById(postId);

        if (postDb.isPresent()) {
            return postDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + postId);
        }
    }

    @Override
    public void deletePost(long postId) {
        Optional<Post> postDb = this.postRepository.findById(postId);

        if (postDb.isPresent()) {
            this.postRepository.delete(postDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + postId);
        }

    }

    @Override
    public List<Post> findByUserId(long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }
}