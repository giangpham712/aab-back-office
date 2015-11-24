package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Expense;
import com.aabplastic.backoffice.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesRestController {

    @Autowired
    private ExpenseService expenseService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Expense> listExpenses(
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

        Page<Expense> result = expenseService.listExpenses(search, page, limit, "name", Sort.Direction.ASC);
        return result.getContent();
    }

    /***
     * Get information of a specific expense
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Expense get(@PathVariable long id) {

        Expense result = expenseService.getExpenseById(id);
        if (result == null)
            throw new ResourceNotFoundException(String.format("Expense with id %d cannot be found", id));

        return result;
    }

    /***
     * Create a new expense
     *
     * @param expense
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Expense create(@RequestBody @Valid Expense expense) {

        Expense created = expenseService.create(expense);
        return created;
    }

    /***
     * Update a specific expense
     *
     * @param expense
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Expense update(@PathVariable long id, @RequestBody @Valid Expense expense) {
        Expense updated = expenseService.update(id, expense);
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

        expenseService.delete(id);
    }

}
