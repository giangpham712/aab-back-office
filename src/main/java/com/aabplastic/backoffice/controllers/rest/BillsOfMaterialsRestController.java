package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.BillOfMaterials;
import com.aabplastic.backoffice.services.BillOfMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/boms")
public class BillsOfMaterialsRestController {

    @Autowired
    private BillOfMaterialsService bomService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BillOfMaterials> listBOMs(Model model, @RequestParam(value = "q", required = false) String search, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {

        if (page == null) {
            page = 1;
        }

        if (limit == null) {
            limit = 1000;
        }

        if (search == null) {
            search = "";
        }

        Page<BillOfMaterials> result = bomService.listBOMs(search, page, limit, "name", Sort.Direction.ASC);
        return result.getContent();
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

    /***
     * Delete a specific bill of materials
     *
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        bomService.delete(id);

    }

}
