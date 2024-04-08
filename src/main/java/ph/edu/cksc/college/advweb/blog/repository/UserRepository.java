package ph.edu.cksc.college.advweb.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ph.edu.cksc.college.advweb.blog.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE " +
            "u.name LIKE CONCAT('%',:query, '%')" +
            "Or u.loginName LIKE CONCAT('%', :query, '%')")
    List<User> searchUsers(String query);

    Optional<User> findByName(String name);

    Boolean existsByName(String name);

    Boolean existsByLoginName(String loginName);
}
