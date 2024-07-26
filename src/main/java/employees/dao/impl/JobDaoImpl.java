package employees.dao.impl;

import employees.config.DatabaseConfig;
import employees.dao.JobDao;
import employees.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {

    private final Connection connection= DatabaseConfig.getConnection();
    @Override
    public void createJobTable() {
        String sql= """
                create table if not exists jobs(
                id serial primary key,
                position varchar(50),
                profession varchar(50),
                description varchar(100),
                experience int
                )
                """;
        try (Statement statement=connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Job table created successfully");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void addJob(Job job) {
        String sql= """
                insert into jobs(position,profession,description,experience)
                values (?,?,?,?)
                """;
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4,job.getExperience());
            preparedStatement.executeUpdate();
            System.out.println("Job added successfully");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Job getJobById(Long jobId) {
        String sql= "select * from jobs where id=?";
        Job job = null;
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setLong(1,jobId);
            try (ResultSet resultSet=preparedStatement.executeQuery()){
                if (resultSet.next()){
                    job = new Job(1L,"Mentor", "Java", "Backend developer", 5);
                    job.setId(resultSet.getLong("id"));
                    job.setPosition(resultSet.getString("position"));
                    job.setProfession(resultSet.getString("profession"));
                    job.setDescription(resultSet.getString("description"));
                    job.setExperience(resultSet.getInt("experience"));
                }

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return job;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        String sql = "select * from jobs order by experience"+(ascOrDesc.equalsIgnoreCase("asc") ? "ASC" : "DESC");
       List<Job> jobs= new ArrayList<>();
       try (Statement statement=connection.createStatement();
       ResultSet resultSet= statement.executeQuery(sql)){
           while (resultSet.next()){
               Job job = new Job(1L,"Mentor", "Java", "Backend developer", 5);
               job.setId(resultSet.getLong("id"));
               job.setPosition(resultSet.getString("position"));
               job.setProfession(resultSet.getString("profession"));
               job.setDescription(resultSet.getString("description"));
               job.setExperience(resultSet.getInt("experience"));
               jobs.add(job);
           }

       }catch (SQLException e){
           System.out.println(e.getMessage());
       }
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        String sql= """
                select j.* from jobs
                join employees e on j.id=e.job_id
                 where id=?
                 """;
        Job job = null;
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setLong(1,employeeId);
            try (ResultSet resultSet=preparedStatement.executeQuery()){
                if (resultSet.next()){
                    job = new Job(1L,"Mentor", "Java", "Backend developer", 5);
                    job.setId(resultSet.getLong("id"));
                    job.setPosition(resultSet.getString("position"));
                    job.setProfession(resultSet.getString("profession"));
                    job.setDescription(resultSet.getString("description"));
                    job.setExperience(resultSet.getInt("experience"));
                }

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return job;
    }

    @Override
    public void deleteDescriptionColumn() {
        String sql= "alter table jobs drop column description";
        try (Statement statement=connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Description column deleted successfully");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
