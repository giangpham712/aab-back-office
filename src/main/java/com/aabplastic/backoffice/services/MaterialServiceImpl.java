package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Material;
import com.aabplastic.backoffice.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Material getMaterialById(long id) {
        Material material = materialRepository.findOne(id);
        return material;
    }

    @Override
    public Material create(Material material) {
        Date now = new Date();

        material.setCreatedAt(now);
        material.setUpdatedAt(now);
        material.setActive(true);

        Material created = materialRepository.save(material);
        return created;
    }

    @Override
    public Material update(long id, Material material) {
        Material updated = materialRepository.findOne(id);

        if (updated == null) {
            throw new ResourceNotFoundException(String.format("Material with id %d cannot be found", id));
        }

        updated.setName(material.getName());
        updated.setDescription(material.getDescription());
        updated.setUnitCost(material.getUnitCost());

        Date now = new Date();
        updated.setUpdatedAt(now);

        updated = materialRepository.save(material);
        return updated;
    }

    @Override
    public void delete(long id) {
        Material deleted = materialRepository.findOne(id);

        if (deleted == null) {
            throw new ResourceNotFoundException(String.format("Material with id %d cannot be found", id));
        }

        Date now = new Date();
        deleted.setDeleted(true);
        deleted.setDeletedAt(now);

        materialRepository.save(deleted);
    }

    @Override
    public Iterable<Material> listMaterials() {
        Iterable<Material> materials = materialRepository.findAll();
        return materials;
    }

    @Override
    public Page<Material> listMaterials(String search, int page, int limit, String sortBy, Sort.Direction sortDirection) {
        PageRequest pageable = new PageRequest(page - 1, limit, new Sort(sortDirection, sortBy));
        Page<Material> materials = materialRepository.findByNameLikeAndDeletedFalse(MessageFormat.format("%{0}%", search), pageable);
        return materials;
    }
}
