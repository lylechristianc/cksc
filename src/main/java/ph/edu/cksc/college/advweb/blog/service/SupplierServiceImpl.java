package ph.edu.cksc.college.advweb.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.edu.cksc.college.advweb.blog.exception.ResourceNotFoundException;
import ph.edu.cksc.college.advweb.blog.model.Supplier;
import ph.edu.cksc.college.advweb.blog.repository.SupplierRepository;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {


    @Autowired
    private SupplierRepository supplierRepository;


    @Override
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {
        Optional<Supplier> supplierDb = this.supplierRepository.findById(supplier.getId());

        if (supplierDb.isPresent()) {
            Supplier supplierUpdate = supplierDb.get();
            supplierUpdate.setId(supplier.getId());
            supplierUpdate.setName(supplier.getName());
            supplierUpdate.setEmail(supplier.getEmail());
            supplierUpdate.setAddress(supplier.getAddress());
            supplierUpdate.setContactInfo(supplier.getContactInfo());
            supplierRepository.save(supplierUpdate);
            return supplierUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + supplier.getId());
        }
    }

    @Override
    public Page<Supplier> getSuppliers(String query, Pageable pageable) {
        return supplierRepository.findByNameContainingOrEmailContainingOrContactInfoContaining(query, query, query, pageable);
    }


    @Override
    public Supplier getSupplierById(long supplierId) {

        Optional<Supplier> supplierDb = this.supplierRepository.findById(supplierId);

        if (supplierDb.isPresent()) {
            return supplierDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + supplierId);
        }
    }

    @Override
    public void deleteSupplier(long supplierId) {
        Optional<Supplier> supplierDb = this.supplierRepository.findById(supplierId);

        if (supplierDb.isPresent()) {
            this.supplierRepository.delete(supplierDb.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + supplierId);
        }

    }
}