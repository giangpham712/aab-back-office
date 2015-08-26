package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.models.dto.ItemDto;
import com.aabplastic.backoffice.services.AutocompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AutocompleteController {

    @Autowired
    private AutocompleteService autocompleteService;

    @RequestMapping("/api/autocomplete/items/{q}.json")
    public @ResponseBody
    List<ItemDto> getItems(@PathVariable("q") String q) {
        return autocompleteService.getItems(q);
    }

}
