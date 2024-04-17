package ph.edu.cksc.college.advweb.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ph.edu.cksc.college.advweb.blog.model.User;
import ph.edu.cksc.college.advweb.blog.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthenticatedUserService {

    @Autowired
    private UserRepository userRepository;

    public boolean hasId(long id){
        String username =  SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByName(username);
        return user.filter(value -> value.getId() == id).isPresent();
    }
}