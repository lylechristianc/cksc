package ph.edu.cksc.college.advweb.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ph.edu.cksc.college.advweb.blog.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
