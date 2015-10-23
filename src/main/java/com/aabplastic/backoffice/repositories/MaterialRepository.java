package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Material;
import com.aabplastic.backoffice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {

}
