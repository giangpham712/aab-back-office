package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Expense;
import com.aabplastic.backoffice.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesRestController {

    @Autowired
    private ExpenseService expenseService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Expense> listExpenses() {
        Iterable<Expense> result = expenseService.listExpenses();
        return result;
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

}
