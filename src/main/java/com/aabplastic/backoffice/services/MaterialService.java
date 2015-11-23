package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface MaterialService {
    Material getMaterialById(long id);

    Material create(Material material);

    Material update(long id, Material material);

    void delete(long id);

    Iterable<Material> listMaterials();

    Page<Material> listMaterials(String search, int page, int limit, String sortBy, Sort.Direction sortDirection);
}
