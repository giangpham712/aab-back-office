package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface MachineService {

    Machine getMachineById(long id);

    Machine create(Machine machine);

    Machine update(long id, Machine machine);

    void delete(long id);

    Page<Machine> listMachines(String search, int page, int limit, String sortBy, Sort.Direction sortDirection);
}
