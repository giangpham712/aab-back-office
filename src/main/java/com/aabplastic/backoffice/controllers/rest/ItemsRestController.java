package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.BillOfMaterials;
import com.aabplastic.backoffice.models.dto.BillOfMaterialsDto;
import com.aabplastic.backoffice.models.dto.ItemDto;
import com.aabplastic.backoffice.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemsRestController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<ItemDto> listItems(@RequestParam(required = true) ItemType type) {

        Iterable<ItemDto> result = itemService.listItemsByType(type, new String[] {"attributes", "billsOfMaterials"});
        return result;
    }

    /***
     * Get information of a specific item
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ItemDto get(@PathVariable long id) {

        ItemDto result = itemService.getItemById(id);
        if (result == null)
            throw new ResourceNotFoundException("Item with requested ID cannot be found");

        return result;
    }

    /***
     * Create a new item
     *
     * @param itemDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@RequestBody @Valid ItemDto itemDto) {


        ItemDto created = itemService.create(itemDto);
        return created;
    }

    /***
     * Update a specific item
     *
     * @param itemDto
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ItemDto update(@PathVariable long id, @RequestBody @Valid ItemDto itemDto) {
        ItemDto updated = itemService.update(id, itemDto);
        return updated;
    }

    @RequestMapping(value = "/{id}/boms", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<BillOfMaterialsDto> listBOMs(@PathVariable long id) {
        Iterable<BillOfMaterialsDto> result = itemService.listBOMsForItem(id);
        return result;
    }

    @RequestMapping(value = "/{id}/boms", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BillOfMaterialsDto createBOM(@PathVariable long id, @RequestBody @Valid BillOfMaterialsDto billOfMaterialsDto) {

        billOfMaterialsDto.setItemId(id);
        BillOfMaterialsDto created = itemService.createBOM(billOfMaterialsDto);

        return created;
    }

    @RequestMapping(value = "/{id}/boms/{bomId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public BillOfMaterialsDto updateBOM(@PathVariable long id, @PathVariable long bomId, @RequestBody @Valid BillOfMaterialsDto billOfMaterialsDto) {

        BillOfMaterialsDto updated = itemService.updateBOM(bomId, billOfMaterialsDto);

        return updated;
    }

    @RequestMapping(value = "/{id}/boms/{bomId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteBOM(@PathVariable long id, @PathVariable long bomId) {

        BillOfMaterialsDto bom = itemService.getBOMById(bomId);

        if (bom == null) {
            throw new ResourceNotFoundException("Bill of materials with requested ID doesn't exist");
        }

        itemService.deleteBOM(bomId);
    }
}
