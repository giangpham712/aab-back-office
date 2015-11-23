package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MaterialRepository extends JpaRepository<Material, Long> {

    Page<Material> findByNameLikeAndDeletedFalse(String format, Pageable pageable);
}
