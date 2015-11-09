package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.BillOfMaterials;
import com.aabplastic.backoffice.services.BillOfMaterialsService;
import com.aabplastic.backoffice.services.MaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Controller
public class BillsOfMaterialsController {

    @Autowired
    private BillOfMaterialsService billOfMaterialsService;

    @Autowired
    private MaterialService materialService;

    @RequestMapping("/boms")
    public String listBOMs(Model model) throws Exception {

        int page = 1;
        int limit = 20;
        String search = "";

        Page<BillOfMaterials> boms = billOfMaterialsService.listBOMs(search, page, limit, "name", Sort.Direction.ASC);
        boms.forEach(x -> {
            x.setItems(new ArrayList<>());
        });

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonBoms = objectMapper.writeValueAsString(boms.getContent());
        model.addAttribute("boms", jsonBoms);

        return "list-boms";
    }

    @RequestMapping("/boms/new")
    public String newBOM(Model model) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonBOM = objectMapper.writeValueAsString(new BillOfMaterials());

        model.addAttribute("headerTitle", "New bill of materials");
        model.addAttribute("mode", "new");
        model.addAttribute("bom", jsonBOM);
        return "edit-bom";
    }

    @RequestMapping("/boms/edit/{id}")
    public String editBOM(@PathVariable long id, Model model) throws Exception {

        BillOfMaterials bom = billOfMaterialsService.getBOMById(id);

        if (bom == null) {
            throw new ResourceNotFoundException("Not found");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonBOM = objectMapper.writeValueAsString(bom);

        model.addAttribute("headerTitle", "Edit bill of materials");
        model.addAttribute("mode", "edit");
        model.addAttribute("bom", jsonBOM);

        return "edit-bom";
    }
}
