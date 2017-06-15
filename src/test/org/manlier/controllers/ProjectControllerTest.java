package org.manlier.controllers;

import org.junit.Before;
import org.junit.Test;
import org.manlier.TestUtil;
import org.manlier.beans.Project;
import org.manlier.beans.Schedule;
import org.manlier.providers.interfaces.IProjectService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by manlier on 2017/6/12.
 */
public class ProjectControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private IProjectService projectService;

    @Resource
    private WebApplicationContext webApplicationContext;

    private String defaultUserId = "97147179147198498";

    @Before
    public void build() {
        Mockito.reset(projectService);

        when(projectService.getInboxPrefix()).thenReturn("inbox_");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddProject() throws Exception {
        Project project = new Project("杂项");
        when(projectService.addProjectForUser(anyString(), Mockito.any(Project.class)))
                .thenReturn(project);

        mockMvc.perform(
                post("/api/project")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(project))
                        .sessionAttr("userId", "nothing")
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andDo(this::printResult);
    }

    @Test
    public void testUpdateProject() throws Exception {
        Project project = new Project("个人事项");

        when(projectService.updateProject(Mockito.any(Project.class)))
                .thenReturn(project);

        mockMvc.perform(
                put("/api/project/{projectId}", "afeac4af01f9eafc")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(project))
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andDo(this::printResult);
    }


    @Test
    public void testDeleteProject() throws Exception {
        when(projectService.removeProject(anyString()))
                .thenReturn(true);

        mockMvc.perform(
                delete("/api/project/{projectId}", "inbox_afeac4af01f9eafc")
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(false)))
                .andDo(this::printResult);
    }

    @Test
    public void testGetAllSchedules() throws Exception {
        List<Schedule> schedules = Arrays.asList(new Schedule("lulu"), new Schedule("dada"));
        when(projectService.getAllSchedulesForProject(anyString()))
                .thenReturn(schedules);

        mockMvc.perform(
                get("/api/project/{projectId}", "feacceajio;j209fe")
        ).andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andDo(this::printResult);
    }
}
