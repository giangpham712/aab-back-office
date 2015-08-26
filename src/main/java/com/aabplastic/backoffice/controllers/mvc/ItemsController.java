package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.dto.ItemDto;
import com.aabplastic.backoffice.services.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/items")
    public String listItems(Model model, @RequestParam Optional<ItemType> type) throws Exception {

        ItemType itemType = type.orElse(ItemType.ASSEMBLY_ITEM);

        Iterable<ItemDto> items = itemService.listItemsByType(itemType, new String[] {"attributes", "billsOfMaterials"});
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        String jsonItems = objectMapper.writeValueAsString(items);
        model.addAttribute("items", jsonItems);

        return "list-items";
    }

    @RequestMapping("/items/new")
    public String newItem(Model model) {

        model.addAttribute("headerTitle", "New item");
        model.addAttribute("type", "new");
        model.addAttribute("item", "{}");
        return "edit-item";
    }

    @RequestMapping("/items/edit/{id}")
    public String editItem(@PathVariable long id, Model model) throws Exception {

        ItemDto item = itemService.getItemById(id);

        if (item == null) {
            throw new ResourceNotFoundException("Not found");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonItems = objectMapper.writeValueAsString(item);

        model.addAttribute("headerTitle", "Edit item");
        model.addAttribute("type", "edit");
        model.addAttribute("item", jsonItems);

        return "edit-item";
    }
}
