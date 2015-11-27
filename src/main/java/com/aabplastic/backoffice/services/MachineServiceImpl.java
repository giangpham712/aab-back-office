package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Machine;
import com.aabplastic.backoffice.repositories.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;

@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    private MachineRepository machineRepository;

    @Override
    public Machine getMachineById(long id) {
        return machineRepository.findOne(id);
    }

    @Override
    public Machine create(Machine machine) {
        Date now = new Date();

        machine.setCreatedAt(now);
        machine.setUpdatedAt(now);

        Machine created = machineRepository.save(machine);
        return created;
    }

    @Override
    public Machine update(long id, Machine machine) {
        Machine updated = machineRepository.findOne(id);

        if (updated == null) {
            throw new ResourceNotFoundException(String.format("Machine with id %d cannot be found", id));
        }

        updated.setName(machine.getName());
        updated.setDescription(machine.getDescription());

        Date now = new Date();
        updated.setUpdatedAt(now);

        updated = machineRepository.save(machine);
        return updated;
    }

    @Override
    public void delete(long id) {
        Machine deleted = machineRepository.findOne(id);

        if (deleted == null) {
            throw new ResourceNotFoundException(String.format("Machine with id %d cannot be found", id));
        }

        Date now = new Date();
        deleted.setDeleted(true);
        deleted.setDeletedAt(now);

        machineRepository.save(deleted);
    }

    @Override
    public Page<Machine> listMachines(String search, int page, int limit, String sortBy, Sort.Direction sortDirection) {
        PageRequest pageable = new PageRequest(page - 1, limit, new Sort(sortDirection, sortBy));
        Page<Machine> machines = machineRepository.findByNameLikeAndDeletedFalse(MessageFormat.format("%{0}%", search), pageable);
        return machines;
    }
}
