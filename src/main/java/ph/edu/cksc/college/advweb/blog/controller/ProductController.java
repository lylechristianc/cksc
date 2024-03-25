package ph.edu.cksc.college.advweb.blog.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ph.edu.cksc.college.advweb.blog.model.Product;
import ph.edu.cksc.college.advweb.blog.service.ProductService;

import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "dir", required = false, defaultValue = "ASC") String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(productService.getProducts(query, pageable));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity <Product> getProductById(@PathVariable long id) {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @PostMapping("/products")
    public ResponseEntity <Product> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok().body(this.productService.createProduct(product));
    }

    @PostMapping(path = "/products/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity <Product> updateProduct(
            @PathVariable long id,
            @Valid @RequestPart Product product,
            @RequestPart (value="picture", required = false) MultipartFile picture) throws IOException {
        product.setId(id);
        return ResponseEntity.ok().body(this.productService.updateProduct(product, picture));
    }

    @DeleteMapping("/products/{id}")
    public HttpStatus deleteProduct(@PathVariable long id) {
        this.productService.deleteProduct(id);
        return HttpStatus.OK;
    }
}