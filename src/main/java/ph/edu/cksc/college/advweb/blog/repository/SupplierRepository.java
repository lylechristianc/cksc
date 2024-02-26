package ph.edu.cksc.college.advweb.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ph.edu.cksc.college.advweb.blog.model.Post;
import ph.edu.cksc.college.advweb.blog.model.Supplier;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Page<Supplier> findByNameContainingOrEmailContainingOrContactInfoContaining(@Param("name") String name, @Param("email") String email, @Param("contactInfo") String contactInfo,  Pageable pageable);
}

