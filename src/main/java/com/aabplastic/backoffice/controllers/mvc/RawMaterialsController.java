package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Material;
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

@Controller
public class RawMaterialsController {

    @Autowired
    private MaterialService materialService;

    @RequestMapping("/materials")
    public String listMaterials(Model model) throws Exception {

        int page = 1;
        int limit = 200;
        String search = "";

        Page<Material> pageMaterials = materialService.listMaterials(search, page, limit, "name", Sort.Direction.ASC);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        String jsonMaterials = objectMapper.writeValueAsString(pageMaterials.getContent());

        model.addAttribute("totalPages", pageMaterials.getTotalPages());
        model.addAttribute("totalProducts", pageMaterials.getTotalElements());
        model.addAttribute("materials", jsonMaterials);
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);


        return "list-materials";
    }

    @RequestMapping("/materials/new")
    public String newMaterial(Model model) {

        model.addAttribute("headerTitle", "New material");
        model.addAttribute("mode", "new");
        model.addAttribute("material", "{}");
        return "edit-material";
    }

    @RequestMapping("/materials/edit/{id}")
    public String editMaterial(@PathVariable long id, Model model) throws Exception {

        Material material = materialService.getMaterialById(id);

        if (material == null) {
            throw new ResourceNotFoundException("Not found");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonMaterials = objectMapper.writeValueAsString(material);

        model.addAttribute("headerTitle", "Edit material");
        model.addAttribute("mode", "edit");
        model.addAttribute("material", jsonMaterials);

        return "edit-material";
    }
}
