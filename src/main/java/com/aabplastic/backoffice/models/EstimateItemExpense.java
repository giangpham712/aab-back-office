package com.aabplastic.backoffice.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Table(name = "estimate_item_expenses")
@Entity
public class EstimateItemExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "expense_id")
    private long expenseId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false, insertable = false, updatable = false)
    private Expense expense;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_item_id", nullable = false)
    private EstimateItem estimateItem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public EstimateItem getEstimateItem() {
        return estimateItem;
    }

    public void setEstimateItem(EstimateItem estimateItem) {
        this.estimateItem = estimateItem;
    }
}
