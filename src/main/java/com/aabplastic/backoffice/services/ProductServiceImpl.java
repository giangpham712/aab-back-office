package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Product;
import com.aabplastic.backoffice.models.ProductionReading;
import com.aabplastic.backoffice.repositories.ProductRepository;
import com.aabplastic.backoffice.repositories.ProductionReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductionReadingRepository readingRepository;

    @Override
    public Product getProductById(long id) {
        Product product = productRepository.findOne(id);
        return product;
    }

    @Override
    public Product create(Product product) {
        Date now = new Date();

        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        product.setActive(true);

        Product created = productRepository.save(product);
        return created;
    }

    @Override
    public Product update(long id, Product product) {

        Product updated = productRepository.findOne(id);

        if (updated == null) {
            throw new ResourceNotFoundException(String.format("Product with id %d cannot be found", id));
        }

        updated.setName(product.getName());
        updated.setDescription(product.getDescription());

        updated.setDefaultBillOfMaterialsId(product.getDefaultBillOfMaterialsId());

        updated.setBagType(product.getBagType());
        updated.setBlowingWidth(product.getBlowingWidth());
        updated.setDescription(product.getDescription());
        updated.setEmboss(product.getEmboss());
        updated.setFilmColor(product.getFilmColor());
        updated.setGusset(product.getGusset());
        updated.setHandleLength(product.getHandleLength());
        updated.setHandleWidth(product.getHandleWidth());
        updated.setLength(product.getLength());
        updated.setMaterial(product.getMaterial());
        updated.setName(product.getName());
        updated.setOuterbagPrinting(product.getOuterbagPrinting());
        updated.setPiecesPerCarton(product.getPiecesPerCarton());
        updated.setPiecesPerOuterbag(product.getPiecesPerOuterbag());
        updated.setPrinting(product.getPrinting());
        updated.setThickness(product.getThickness());
        updated.setWidth(product.getWidth());

        updated.setMasterBatchAmount(product.getMasterBatchAmount());   // Master Batch Amount
        updated.setMasterBatchCode(product.getMasterBatchCode());
        updated.setInkCode(product.getInkCode());
        updated.setInkAmount(product.getInkAmount());
        updated.setPaintPerUnitWeight(product.getPaintPerUnitWeight());

        updated.setRecycleRate(product.getRecycleRate());

        Date now = new Date();
        updated.setUpdatedAt(now);

        updated = productRepository.save(product);
        return updated;
    }

    @Override
    public void delete(long id) {
        Product deleted = productRepository.findOne(id);

        if (deleted == null) {
            throw new ResourceNotFoundException(String.format("Product with id %d cannot be found", id));
        }

        Date now = new Date();
        deleted.setDeleted(true);
        deleted.setDeletedAt(now);

        productRepository.save(deleted);
    }

    @Override
    public Iterable<Product> listProducts() {
        Iterable<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Page<Product> listProducts(String search, int page, int limit, String sortBy, Sort.Direction sortDirection) {

        PageRequest pageable = new PageRequest(page - 1, limit, new Sort(sortDirection, sortBy));
        Page<Product> products = productRepository.findByNameLikeAndDeletedFalse(MessageFormat.format("%{0}%", search), pageable);
        return products;
    }

    @Override
    public Iterable<ProductionReading> listReadingsByProductId(long id) {
        Iterable<ProductionReading> readings = readingRepository.findByProductId(id);
        return readings;
    }

    @Override
    public ProductionReading createReading(long id, ProductionReading reading) {
        Date now = new Date();

        reading.setCreatedAt(now);
        reading.setUpdatedAt(now);
        reading.setProductId(id);

        ProductionReading created = readingRepository.save(reading);
        return created;
    }

    @Override
    public void deleteReading(long id) {
        readingRepository.delete(id);
    }
}
