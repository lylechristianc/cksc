package ph.edu.cksc.college.advweb.blog.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.edu.cksc.college.advweb.blog.model.Supplier;
import ph.edu.cksc.college.advweb.blog.service.SupplierService;


import java.util.List;


@CrossOrigin("*")
@RestController
public class SupplierController {


    @Autowired
    private SupplierService SupplierService;


    //pagination
    @GetMapping("/Suppliers")
    public ResponseEntity<List<Supplier>> getSuppliers(
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "dir", required = false, defaultValue = "ASC") String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(SupplierService.getSuppliers(query, pageable).getContent());
    }


    @GetMapping("/Suppliers/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable long id) {
        return ResponseEntity.ok().body(SupplierService.getSupplierById(id));
    }


    @PostMapping("/Suppliers")
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody Supplier Supplier) {
        return ResponseEntity.ok().body(this.SupplierService.createSupplier(Supplier));
    }


    @PutMapping("/Suppliers/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable long id, @Valid @RequestBody Supplier Supplier) {
        Supplier.setId(id);
        return ResponseEntity.ok().body(this.SupplierService.updateSupplier(Supplier));
    }


    @DeleteMapping("/Suppliers/{id}")
    public HttpStatus deleteSupplier(@PathVariable long id) {
        this.SupplierService.deleteSupplier(id);
        return HttpStatus.OK;
    }
}



