package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.BillOfMaterials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillOfMaterialsRepository extends JpaRepository<BillOfMaterials, Long> {

    Page<BillOfMaterials> findByNameLikeAndDeletedFalse(String format, Pageable pageable);
}
