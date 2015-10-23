package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.BillOfMaterials;
import com.aabplastic.backoffice.services.BillOfMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/boms")
public class BillOfMaterialsRestController {

    @Autowired
    private BillOfMaterialsService bomService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<BillOfMaterials> listBOMs() {
        Iterable<BillOfMaterials> result = bomService.listBOMs();
        return result;
    }

    /***
     * Get information of a specific bom
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public BillOfMaterials get(@PathVariable long id) {

        BillOfMaterials result = bomService.getBOMById(id);
        if (result == null)
            throw new ResourceNotFoundException(String.format("BillOfMaterials with id %d cannot be found", id));

        return result;
    }

    /***
     * Create a new bom
     *
     * @param bom
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BillOfMaterials create(@RequestBody @Valid BillOfMaterials bom) {

        BillOfMaterials created = bomService.create(bom);
        return created;
    }

    /***
     * Update a specific bom
     *
     * @param bom
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public BillOfMaterials update(@PathVariable long id, @RequestBody @Valid BillOfMaterials bom) {
        BillOfMaterials updated = bomService.update(id, bom);
        return updated;
    }

}
