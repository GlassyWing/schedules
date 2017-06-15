package org.manlier.providers;

import org.junit.Assert;
import org.junit.Test;
import org.manlier.beans.Project;
import org.manlier.beans.Schedule;
import org.manlier.providers.interfaces.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by manlier on 2017/6/11.
 */
public class ProjectServiceTest extends BaseServiceTest {

    @Autowired
    private IProjectService projectService;

    private String defaultUserId = "97147179147198498";

    private String defaultProjectId = "571ee0c84f7811e79a9054a050631fca";

    @Test
    public void testAddProject() {
        Project project = new Project("杂项");
        project.setProjectUuid(defaultProjectId);
        Project affected = projectService.addProjectForUser(defaultUserId, project);
        Assert.assertNotNull(affected);
    }

    @Test
    public void testGetProjectById() {
        String projectId = defaultProjectId;
        Project project = projectService.getProjectById(projectId);
        System.out.println(project);
    }

    @Test
    public void testGetAllProjects() {
        List<Project> projects = projectService.getAllProjectForUser(defaultUserId);
        System.out.println(projects);
    }

    @Test
    public void testUpdateProject() {
        String projectId = defaultProjectId;
        Project project = projectService.getProjectById(projectId);
        project.setTitle("学习");
        Project affected = projectService.updateProject(project);
        Assert.assertNotNull(affected);
    }

    @Test
    public void testDeleteProject() {
        boolean affected = projectService.removeProject(defaultProjectId);
        Assert.assertTrue(affected);
    }

    @Test
    public void testGetAllSchedulesForProject() {
        List<Schedule> schedules = projectService.getAllSchedulesForProject("fa");
        System.out.println(schedules);
    }
}
