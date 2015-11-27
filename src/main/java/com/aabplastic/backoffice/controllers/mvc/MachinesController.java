package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Machine;
import com.aabplastic.backoffice.services.MachineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;

@Controller
public class MachinesController {

    @Autowired
    private MachineService machineService;

    @RequestMapping("/machines")
    public String listMachines(Model model) throws Exception {

        int page = 1;
        int limit = 200;
        String search = "";

        Page<Machine> pagedMachines = machineService.listMachines(search, page, limit, "name", Sort.Direction.ASC);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        String jsonMachines = objectMapper.writeValueAsString(pagedMachines.getContent());

        model.addAttribute("totalPages", pagedMachines.getTotalPages());
        model.addAttribute("totalProducts", pagedMachines.getTotalElements());
        model.addAttribute("machines", jsonMachines);
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);

        return "list-machines";
    }

    @RequestMapping("/machines/new")
    public String newMachine(Model model) {

        model.addAttribute("headerTitle", "New machine");
        model.addAttribute("mode", "new");
        model.addAttribute("machine", "{}");
        return "edit-machine";
    }

    @RequestMapping("/machines/edit/{id}")
    public String editMachine(@PathVariable long id, Model model) throws Exception {

        Machine machine = machineService.getMachineById(id);

        if (machine == null) {
            throw new ResourceNotFoundException("Not found");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonMachines = objectMapper.writeValueAsString(machine);

        model.addAttribute("headerTitle", "Edit machine");
        model.addAttribute("mode", "edit");
        model.addAttribute("machine", jsonMachines);

        return "edit-machine";
    }
}
