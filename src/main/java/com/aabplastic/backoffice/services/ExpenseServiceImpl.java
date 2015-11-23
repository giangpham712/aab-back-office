package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Expense;
import com.aabplastic.backoffice.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Iterable<Expense> listExpenses() {

        return expenseRepository.findByDeletedFalse();
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
        return expenseRepository.findAllByDefaultExpenseTrueAndDeletedFalse();
    }

    @Override
    public void delete(long id) {
        Expense deleted = expenseRepository.findOne(id);

        if (deleted == null) {
            throw new ResourceNotFoundException(String.format("Expense with id %d cannot be found", id));
        }

        Date now = new Date();
        deleted.setDeleted(true);
        deleted.setDeletedAt(now);

        expenseRepository.save(deleted);
    }

    @Override
    public Page<Expense> listExpenses(String search, int page, int limit, String sortBy, Sort.Direction sortDirection) {
        PageRequest pageable = new PageRequest(page - 1, limit, new Sort(sortDirection, sortBy));
        Page<Expense> expenses = expenseRepository.findByNameLikeAndDeletedFalse(MessageFormat.format("%{0}%", search), pageable);
        return expenses;
    }
}
