package ph.edu.cksc.college.advweb.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ph.edu.cksc.college.advweb.blog.model.Supplier;

import java.util.List;

public interface SupplierService {
    Supplier createSupplier(Supplier supplier);

    Supplier updateSupplier(Supplier supplier);

    Page<Supplier> getSuppliers(String query, Pageable pageable);

    Supplier getSupplierById(long supplierId);

    void deleteSupplier(long id);
}
