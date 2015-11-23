package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Iterable<Expense> findAllByDefaultExpenseTrueAndDeletedFalse();

    Iterable<Expense> findByDeletedFalse();

    Page<Expense> findByNameLikeAndDeletedFalse(String format, Pageable pageable);
}
