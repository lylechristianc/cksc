package ph.edu.cksc.college.advweb.blog.service;

import ph.edu.cksc.college.advweb.blog.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    List<User> getUsers(String query);

    User getUserById(long userId);

    void deleteUser(long id);
}