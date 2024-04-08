package ph.edu.cksc.college.advweb.blog.service;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ph.edu.cksc.college.advweb.blog.exception.ResourceNotFoundException;
import ph.edu.cksc.college.advweb.blog.model.Product;
import ph.edu.cksc.college.advweb.blog.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl() throws IOException {
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    private final Path UPLOAD_PATH =
            Paths.get(new ClassPathResource("").getFile().getAbsolutePath() + File.separator + "static"  + File.separator + "images");

    @Override
    public Product updateProduct(Product product, MultipartFile picture) throws IOException {
        Optional<Product> productDb = this.productRepository.findById(product.getId());

        if (productDb.isPresent()) {
            Product productUpdate = productDb.get();
            if (picture != null) {
                if (!Files.exists(UPLOAD_PATH)) {
                    Files.createDirectories(UPLOAD_PATH);
                }
                // file format validation
                String contentType = picture.getContentType();
                if (contentType != null && !contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                    throw new IOException("Only .jpeg and .png images are " + "supported");
                }
                String timeStampedFileName = new SimpleDateFormat("ssmmHHddMMyyyy")
                        .format(new Date()) + "_" + picture.getOriginalFilename();
                Path filePath = UPLOAD_PATH.resolve(timeStampedFileName);
                Files.copy(picture.getInputStream(), filePath);
                // TODO place to database table field Product.picture later
                productUpdate.setPicture(timeStampedFileName);
            }
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
    public Page<Product> getProducts(String query, Pageable pageable) {
        //return productRepository.searchProducts(query, pageable);
        return productRepository.findByNameContainingOrDescriptionContaining(query, query, pageable);
    }

    @Override
    public Product getProductById(long productId) {

        Optional<Product> productDb = this.productRepository.findById(productId);

        if (productDb.isPresent()) {
            return productDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + productId);
        }
    }

    @Override
    public void deleteProduct(long productId) {
        Optional<Product> productDb = this.productRepository.findById(productId);

        if (productDb.isPresent()) {
            this.productRepository.delete(productDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + productId);
        }

    }
}