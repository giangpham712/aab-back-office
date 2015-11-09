package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.BillOfMaterials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface BillOfMaterialsService {
    Iterable<BillOfMaterials> listBOMs();

    BillOfMaterials getBOMById(long id);

    BillOfMaterials create(BillOfMaterials bom);

    BillOfMaterials update(long id, BillOfMaterials bom);

    void delete(long id);

    Page<BillOfMaterials> listBOMs(String search, int page, int limit, String sortBy, Sort.Direction sortDirection);
}
