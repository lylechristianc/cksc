package ph.edu.cksc.college.advweb.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ph.edu.cksc.college.advweb.blog.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    Product updateProduct(Product product, MultipartFile picture) throws IOException;

    Page<Product> getProducts(String query, Pageable pageable);

    Product getProductById(long productId);

    void deleteProduct(long id);
}