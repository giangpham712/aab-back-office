package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Expense;

public interface ExpenseService {
    Iterable<Expense> listExpenses();

    Expense getExpenseById(long id);

    Expense create(Expense expense);

    Expense update(long id, Expense expense);
}
