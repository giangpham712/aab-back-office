package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface ExpenseService {
    Iterable<Expense> listExpenses();

    Expense getExpenseById(long id);

    Expense create(Expense expense);

    Expense update(long id, Expense expense);

    Iterable<Expense> listDefaultExpenses();

    void delete(long id);

    Page<Expense> listExpenses(String search, int page, int limit, String sortBy, Sort.Direction sortDirection);
}
