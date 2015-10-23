package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Material;

public interface MaterialService {
    Material getMaterialById(long id);

    Material create(Material material);

    Material update(long id, Material material);

    void delete(long id);

    Iterable<Material> listMaterials();
}
