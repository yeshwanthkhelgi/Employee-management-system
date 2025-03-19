package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Payroll;
import net.javaguides.springboot.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    // View all payrolls
    @GetMapping
    public String viewPayrolls(Model model) {
        List<Payroll> payrollList = payrollService.getAllPayrolls();
        model.addAttribute("payrollList", payrollList);
        return "payroll_list";
    }

    // Add payroll form
    @GetMapping("/add")
    public String showAddPayrollForm(Model model) {
        model.addAttribute("payroll", new Payroll());
        return "add_payroll";
    }

    // Save payroll
    @PostMapping("/save")
    public String savePayroll(
            @RequestParam Long employeeId,
            @RequestParam BigDecimal baseSalary,
            @RequestParam BigDecimal bonus,
            @RequestParam BigDecimal deductions) {

        payrollService.createPayroll(employeeId, baseSalary, bonus, deductions);
        return "redirect:/payroll";
    }
    
    @GetMapping("/showFormForUpdatePayroll/{id}")
    public String showFormForUpdate(@PathVariable Long id, Model model) {
        Payroll payroll = payrollService.getPayrollById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found with ID: " + id));
        model.addAttribute("payroll", payroll);
        return "update_payroll";
    }
    
    @PostMapping("/update")
    public String updatePayroll(
            @RequestParam Long id,
            @RequestParam BigDecimal baseSalary,
            @RequestParam BigDecimal bonus,
            @RequestParam BigDecimal deductions) {

        payrollService.updatePayroll(id, baseSalary, bonus, deductions);
        return "redirect:/payroll";
    }
    
    @GetMapping("/deletePayroll/{id}")
    public String deletePayroll(@PathVariable Long id) {
        payrollService.deletePayroll(id);
        return "redirect:/payroll";
    }
}
