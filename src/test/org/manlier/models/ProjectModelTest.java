package org.manlier.models;

import org.junit.Assert;
import org.junit.Test;
import org.manlier.beans.Project;
import org.manlier.beans.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by manlier on 2017/6/5.
 */
public class ProjectModelTest extends DBConnectTest {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private ProjectDao projectDao;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserDao userDao;

    private String defaultUserId = "97147179147198498";

    @Test
    public void getProject() {
        Project project = projectDao.getProjectByProjectId("inbox_97147179147198488");
        System.out.println(project);
    }

    @Test
    public void getProjectForUser() {
        List<Project> projects = projectDao.getAllProjectsForUser(defaultUserId);
        Assert.assertNotNull(projects);
        System.out.println(projects);
    }


    @Test
    public void addProject() {
        Project project = new Project("个人事务");
        int num = projectDao.addProjectForUser("97147179147198488",project);
        Assert.assertEquals(num, 1);
    }

    @Test
    public void selectProjectExcept() {
        List<Project> projects = projectDao.getAllProjectsForUserExcept("97147179147198488", "inbox_97147179147198488");
        System.out.println(projects);
    }

    @Test
    public void deleteProject() {
        User user = new User("panda_veir@gmail.com", "padddannc");
        userDao.addUser(user);
        Project project = new Project("杂务");
        projectDao.addProjectForUser(user.getUserUuid(), project);
        int affected = projectDao.deleteProject(project.getProjectUuid());
        Assert.assertEquals(1, affected);
    }

    @Test
    public void deleteProjectCascadeUser() {
        User user = new User("panda_veir@gmail.com", "padddannc");
        userDao.addUser(user);
        Project project = new Project("杂务");
        projectDao.addProjectForUser(user.getUserUuid(), project);
        userDao.deleteUser(user.getUserUuid());
        List<Project> projects = projectDao.getAllProjectsForUserExcept(user.getUserUuid(),
                "inbox_" + user.getUserUuid());
        Assert.assertEquals(0, projects.size());
    }
}
