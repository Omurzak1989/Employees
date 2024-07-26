package employees.dao.impl;

import employees.config.DatabaseConfig;
import employees.dao.EmployeeDao;
import employees.models.Employee;
import employees.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {

    private final Connection connection= DatabaseConfig.getConnection();


    @Override
    public void createEmployeeTable() {
        String sql="""
    create table if not exists employees(
    id serial primary key,
    first_name varchar(255),
    last_name varchar(255),
    age int,
    email varchar(255),
    job_id int references jobs(id)
    )
    """;
        try(Statement statement=connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Employee table created successfully");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = """
                insert into employees(first_name, last_name, age, email, job_id)
                values(?, ?, ?, ?, ?)
                """;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setInt(3,employee.getAge());
            preparedStatement.setString(4,employee.getEmail());
            preparedStatement.setInt(5,employee.getJobId());
            preparedStatement.executeUpdate();
            System.out.println("Employee added successfully");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void dropTable() {
        String sql = "drop table if exists employees";
        try (Statement statement=connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Employee table dropped successfully");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void cleanTable() {
        String sql= "TRUNCATE TABLE employees";
        try(Statement statement=connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Employee table cleaned successfully");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String sql= """
                update employees
                set first_name=?,last_name=?,age=?,email=?,job_id=? 
                where id=?
                """;
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            preparedStatement.setString(1,employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setInt(3,employee.getAge());
            preparedStatement.setString(4,employee.getEmail());
            preparedStatement.setInt(5,employee.getJobId());
            preparedStatement.setLong(6,id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated>0){
                System.out.println("Employee table updated successfully");
            }else {
                System.out.println("Employee not found");
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Employee> getAllEmployees() {
        String sql="select * from employees";
        List<Employee> employees=new ArrayList<>();
        try(Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql)){
                while (resultSet.next()){
                    Employee employee=new Employee();
                    employee.setId(resultSet.getLong("id"));
                    employee.setFirstName(resultSet.getString("first_name"));
                    employee.setLastName(resultSet.getString("last_name"));
                    employee.setAge(resultSet.getInt("age"));
                    employee.setEmail(resultSet.getString("email"));
                    employee.setJobId(resultSet.getInt("job_id"));
                    employees.add(employee);
                }
            }catch (SQLException e){
            System.out.println(e.getMessage());

        }
        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        String sql="select * from employees where email=?";
        Employee employee=null;
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            preparedStatement.setString(1,email);
            try (ResultSet resultSet=preparedStatement.executeQuery()){
                if (resultSet.next()){
                    employee=new Employee();
                    employee.setId(resultSet.getLong("id"));
                    employee.setFirstName(resultSet.getString("first_name"));
                    employee.setLastName(resultSet.getString("last_name"));
                    employee.setAge(resultSet.getInt("age"));
                    employee.setEmail(resultSet.getString("email"));
                    employee.setJobId(resultSet.getInt("job_id"));

                }

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        String sql= """
                select e.*,j.id as job_id,j.position,j.profession,j.description,j.experience
                from employees e
                join jobs j on e.job_id=j.id
                where e.id=?
                """;
        Map<Employee, Job> employeeJobMap=new HashMap<>();
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setLong(1,employeeId);
            try (ResultSet resultSet=preparedStatement.executeQuery()){
                if (resultSet.next()){
                    Employee employee=new Employee();
                    employee.setId(resultSet.getLong("e.id"));
                    employee.setFirstName(resultSet.getString("e.first_name"));
                    employee.setLastName(resultSet.getString("e.last_name"));
                    employee.setAge(resultSet.getInt("e.age"));
                    employee.setEmail(resultSet.getString("e.email"));
                    employee.setJobId(resultSet.getInt("e.job_id"));

                    Job job=new Job(1L,"Mentor", "Java", "Backend developer", 5);
                    job.setId(resultSet.getLong("job_id"));
                    job.setPosition(resultSet.getString("position"));
                    job.setProfession(resultSet.getString("profession"));
                    job.setDescription(resultSet.getString("description"));
                    job.setExperience(resultSet.getInt("experience"));

                    employeeJobMap.put(employee,job);
                }

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employeeJobMap;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        String sql= """
                select e.* from employees e 
                join jobs j on e.job_id=j.id
                where position=?";"
                """;
        List<Employee> employees=new ArrayList<>();
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1,position);
            try (ResultSet resultSet=preparedStatement.executeQuery()){
                while (resultSet.next()){
                    Employee employee=new Employee();
                    employee.setId(resultSet.getLong("e.id"));
                    employee.setFirstName(resultSet.getString("e.first_name"));
                    employee.setLastName(resultSet.getString("e.last_name"));
                    employee.setAge(resultSet.getInt("e.age"));
                    employee.setEmail(resultSet.getString("e.email"));
                    employee.setJobId(resultSet.getInt("e.job_id"));
                    employees.add(employee);
                }

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }
}
