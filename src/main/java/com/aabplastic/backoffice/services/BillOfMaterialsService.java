package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.BillOfMaterials;

public interface BillOfMaterialsService {
    Iterable<BillOfMaterials> listBOMs();

    BillOfMaterials getBOMById(long id);

    BillOfMaterials create(BillOfMaterials bom);

    BillOfMaterials update(long id, BillOfMaterials bom);
}
