package employees.service.impl;

import employees.dao.JobDao;
import employees.dao.impl.JobDaoImpl;
import employees.models.Job;
import employees.service.JobService;

import java.util.List;

public class JobServiceImpl implements JobService {
    JobDao jobDao=new JobDaoImpl();
    @Override
    public void createJobTable() {
        jobDao.createJobTable();

    }

    @Override
    public void addJob(Job job) {
        jobDao.addJob(job);

    }

    @Override
    public Job getJobById(Long jobId) {
        return jobDao.getJobById(jobId);
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        return jobDao.sortByExperience(ascOrDesc);
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        return jobDao.getJobByEmployeeId(employeeId);
    }

    @Override
    public void deleteDescriptionColumn() {
        jobDao.deleteDescriptionColumn();
    }
}
