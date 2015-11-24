package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Material;
import com.aabplastic.backoffice.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/materials")
public class RawMaterialsRestController {

    @Autowired
    private MaterialService materialService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Material> listMaterials(
            Model model,
            @RequestParam(value = "q", required = false) String search,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit
    ) {
        if (page == null) {
            page = 1;
        }

        if (limit == null) {
            limit = 20;
        }

        if (search == null) {
            search = "";
        }

        Page<Material> result = materialService.listMaterials(search, page, limit, "name", Sort.Direction.ASC);
        return result.getContent();
    }

    /***
     * Get information of a specific material
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Material get(@PathVariable long id) {

        Material result = materialService.getMaterialById(id);
        if (result == null)
            throw new ResourceNotFoundException(String.format("Material with id %d cannot be found", id));

        return result;
    }

    /***
     * Create a new material
     *
     * @param materialDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Material create(@RequestBody @Valid Material materialDto) {

        Material created = materialService.create(materialDto);
        return created;
    }

    /***
     * Update a specific material
     *
     * @param materialDto
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Material update(@PathVariable long id, @RequestBody @Valid Material materialDto) {
        Material updated = materialService.update(id, materialDto);
        return updated;
    }

    /***
     * Update a specific expense
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {

        materialService.delete(id);
    }
}
