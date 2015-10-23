package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.BillOfMaterials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillOfMaterialsRepository extends JpaRepository<BillOfMaterials, Long> {

}
