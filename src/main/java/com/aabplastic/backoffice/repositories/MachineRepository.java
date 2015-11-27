package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    Page<Machine> findByNameLikeAndDeletedFalse(String format, Pageable pageable);
}
