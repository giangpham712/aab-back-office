package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Machine;
import com.aabplastic.backoffice.services.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/machines")
public class MachinesRestController {

    @Autowired
    private MachineService machineService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Machine> listMachines(
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

        Page<Machine> result = machineService.listMachines(search, page, limit, "name", Sort.Direction.ASC);
        return result.getContent();
    }

    /***
     * Get information of a specific machine
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Machine get(@PathVariable long id) {

        Machine result = machineService.getMachineById(id);
        if (result == null)
            throw new ResourceNotFoundException(String.format("Machine with id %d cannot be found", id));

        return result;
    }

    /***
     * Create a new machine
     *
     * @param machine
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Machine create(@RequestBody @Valid Machine machine) {

        Machine created = machineService.create(machine);
        return created;
    }

    /***
     * Update a specific machine
     *
     * @param machine
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Machine update(@PathVariable long id, @RequestBody @Valid Machine machine) {
        Machine updated = machineService.update(id, machine);
        return updated;
    }

    /***
     * Update a specific machine
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {

        machineService.delete(id);
    }

}
