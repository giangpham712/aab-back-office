package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Expense;
import com.aabplastic.backoffice.services.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;

@Controller
public class ExpensesController {

    @Autowired
    private ExpenseService expenseService;

    @RequestMapping("/expenses")
    public String listExpenses(Model model) throws Exception {

        Iterable<Expense> expenses = expenseService.listExpenses();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        String jsonExpenses = objectMapper.writeValueAsString(expenses);
        model.addAttribute("expenses", jsonExpenses);

        return "list-expenses";
    }

    @RequestMapping("/expenses/new")
    public String newExpense(Model model) {

        model.addAttribute("headerTitle", "New expense");
        model.addAttribute("mode", "new");
        model.addAttribute("expense", "{}");
        return "edit-expense";
    }

    @RequestMapping("/expenses/edit/{id}")
    public String editExpense(@PathVariable long id, Model model) throws Exception {

        Expense expense = expenseService.getExpenseById(id);

        if (expense == null) {
            throw new ResourceNotFoundException("Not found");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        String jsonExpenses = objectMapper.writeValueAsString(expense);

        model.addAttribute("headerTitle", "Edit expense");
        model.addAttribute("mode", "edit");
        model.addAttribute("expense", jsonExpenses);

        return "edit-expense";
    }
}
