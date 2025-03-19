package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Payroll;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Create payroll record
    public Payroll createPayroll(Long employeeId, BigDecimal baseSalary, BigDecimal bonus, BigDecimal deductions) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        BigDecimal netSalary = baseSalary.add(bonus).subtract(deductions);

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setBaseSalary(baseSalary);
        payroll.setBonus(bonus);              // Fixed field name
        payroll.setDeductions(deductions);
        payroll.setNetSalary(netSalary);
        payroll.setPayDate(LocalDate.now());

        return payrollRepository.save(payroll);
    }

    // Retrieve all payrolls
    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    // Get payroll by employee ID
    public List<Payroll> getPayrollByEmployeeId(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }
    
    public Optional<Payroll> getPayrollById(Long id) {
        return payrollRepository.findById(id);
    }
    
    public void updatePayroll(Long id, BigDecimal baseSalary, BigDecimal bonus, BigDecimal deductions) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        BigDecimal netSalary = baseSalary.add(bonus).subtract(deductions);
        payroll.setBaseSalary(baseSalary);
        payroll.setBonus(bonus);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(netSalary);

        payrollRepository.save(payroll);
    }
    
    public void deletePayroll(Long id) {
        payrollRepository.deleteById(id);
    }
}
