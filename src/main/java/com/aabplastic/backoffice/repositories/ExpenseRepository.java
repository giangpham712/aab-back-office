package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Iterable<Expense> findAllByDefaultExpenseTrue();
}
