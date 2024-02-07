package ph.edu.cksc.college.advweb.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.edu.cksc.college.advweb.blog.exception.ResourceNotFoundException;
import ph.edu.cksc.college.advweb.blog.model.Product;
import ph.edu.cksc.college.advweb.blog.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> productEntry = this.productRepository.findById(product.getId());

        if (productEntry.isPresent()) {
            Product productUpdate = productEntry.get();
            productUpdate.setId(product.getId());
            productUpdate.setName(product.getName());
            productUpdate.setDescription(product.getDescription());
            productUpdate.setPrice(product.getPrice());
            productRepository.save(productUpdate);
            return productUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + product.getId());
        }
    }

    @Override
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(long productId) {

        Optional <Product> productEntry = this.productRepository.findById(productId);

        if (productEntry.isPresent()) {
            return productEntry.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + productId);
        }
    }

    @Override
    public void deleteProduct(long productId) {
        Optional <Product> productEntry = this.productRepository.findById(productId);

        if (productEntry.isPresent()) {
            this.productRepository.delete(productEntry.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + productId);
        }

    }
}