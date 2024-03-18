package ph.edu.cksc.college.advweb.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ph.edu.cksc.college.advweb.blog.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    Product updateProduct(Product product);

    Page<Product> getProducts(String query, Pageable pageable);

    Product getProductById(long productId);

    void deleteProduct(long id);
}