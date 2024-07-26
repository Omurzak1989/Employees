package employees.service;

import employees.models.Employee;
import employees.models.Job;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    void createEmployeeTable();
    void addEmployee(Employee employee);
    void dropTable();
    void cleanTable();
    void updateEmployee(Long id,Employee employee);
    List<Employee> getAllEmployees();
    Employee findByEmail(String email);
    Map<Employee, Job> getEmployeeById(Long employeeId);
    List<Employee> getEmployeeByPosition(String position);
}
