package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Expense;
import com.aabplastic.backoffice.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Iterable<Expense> listExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense getExpenseById(long id) {
        return expenseRepository.findOne(id);
    }

    @Override
    public Expense create(Expense expense) {
        Date now = new Date();

        expense.setCreatedAt(now);
        expense.setUpdatedAt(now);

        Expense created = expenseRepository.save(expense);
        return created;
    }

    @Override
    public Expense update(long id, Expense expense) {
        Expense updated = expenseRepository.findOne(id);

        if (updated == null) {
            throw new ResourceNotFoundException(String.format("Expense with id %d cannot be found", id));
        }

        updated.setName(expense.getName());
        updated.setDescription(expense.getDescription());

        Date now = new Date();
        updated.setUpdatedAt(now);

        updated = expenseRepository.save(expense);
        return updated;
    }

    @Override
    public Iterable<Expense> listDefaultExpenses() {
        return expenseRepository.findAllByDefaultExpenseTrue();
    }
}
