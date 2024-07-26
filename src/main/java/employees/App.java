package employees;

import employees.config.DatabaseConfig;
import employees.models.Employee;
import employees.models.Job;
import employees.service.EmployeeService;
import employees.service.JobService;
import employees.service.impl.EmployeeServiceImpl;
import employees.service.impl.JobServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        EmployeeService employeeService = new EmployeeServiceImpl();
        JobService jobService = new JobServiceImpl();

        // Job таблицасын түзүү жана маалыматтарды кошуу
        jobService.createJobTable();
        jobService.addJob(new Job(2L,"Mentor", "Java", "Backend developer", 5));
        jobService.addJob(new Job(3L,"Management", "JavaScript", "Frontend developer", 3));

        // Employee таблицасын түзүү жана маалыматтарды кошуу
        employeeService.createEmployeeTable();
        employeeService.addEmployee(new Employee(1L,"John", "Doe", 30, "john.doe@example.com", 1));
        employeeService.addEmployee(new Employee(2L,"Jane", "Smith", 25, "jane.smith@example.com", 2));

        // Employee маалыматтарын жаңыртуу
        employeeService.updateEmployee(1L, new Employee(1L,"John", "Doe", 31, "john.doe@update.com", 1));

        // Бардык Employee жазууларын алуу
        List<Employee> employees = employeeService.getAllEmployees();
        employees.forEach(System.out::println);

        // Email боюнча Employee табуу
        Employee employee = employeeService.findByEmail("jane.smith@example.com");
        System.out.println(employee);

        // ID боюнча Employee жана анын Job маалыматтарын алуу
        Map<Employee, Job> employeeJob = employeeService.getEmployeeById(1L);
        employeeJob.forEach((emp, job) -> {
            System.out.println("Employee: " + emp);
            System.out.println("Job: " + job);
        });

        // Position боюнча Employee жазууларын алуу
        List<Employee> mentors = employeeService.getEmployeeByPosition("Mentor");
        mentors.forEach(System.out::println);

        // Тажрыйба боюнча Job жазууларын сорттоо
        List<Job> sortedJobsAsc = jobService.sortByExperience("asc");
        sortedJobsAsc.forEach(System.out::println);

        // Employee ID боюнча Job маалыматтарын алуу
        Job job = jobService.getJobByEmployeeId(1L);
        System.out.println(job);

        // Description тилкесин өчүрүү
        jobService.deleteDescriptionColumn();


    }
}
