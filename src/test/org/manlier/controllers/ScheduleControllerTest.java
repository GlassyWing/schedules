package org.manlier.controllers;

import org.junit.Before;
import org.junit.Test;
import org.manlier.TestUtil;
import org.manlier.beans.Schedule;
import org.manlier.beans.User;
import org.manlier.customs.temporal.TheBeginOfDayAdjuster;
import org.manlier.customs.temporal.TheEndOfDayAdjuster;
import org.manlier.dto.TaskDto;
import org.manlier.providers.interfaces.INotificationService;
import org.manlier.providers.interfaces.IPreferencesService;
import org.manlier.providers.interfaces.IScheduleService;
import org.manlier.providers.interfaces.IUserService;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

/**
 * Created by manlier on 2017/6/8.
 */
public class ScheduleControllerTest extends BaseControllerTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private MockMvc mockMvc;

    @Resource
    private IScheduleService scheduleService;

    @Resource
    private IUserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;


    private ModelMapper modelMapper = new ModelMapper();

    private User user = new User("140194810@qq.com", "feahulcbvalfe");

    @Before
    public void setup() {
        Mockito.reset(scheduleService, userService);

        user.setUserUuid("afeaceahualfhgu3ajfiea131a");
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddSchedule() throws Exception {
        TaskDto taskDto = new TaskDto("看书看书", "记得在图书馆看书，多带几盒饼干");
        taskDto.setStartTime(Instant.now().with(new TheBeginOfDayAdjuster()));
        taskDto.setDueTime(Instant.now().with(new TheEndOfDayAdjuster()));

        Schedule schedule = modelMapper.map(taskDto, Schedule.class);

        when(scheduleService.addScheduleForUser(anyString(), Mockito.any(Schedule.class)))
                .thenReturn(schedule);

        mockMvc.perform(
                post("/api/task")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(taskDto))
        )
                .andExpect(status().is(200))
                .andDo(this::printResult);
    }

    @Test
    public void testUpdateSchedule() throws Exception {
        TaskDto taskDto = new TaskDto("看书看书", "记得在图书馆看书，多带几盒饼干");
        taskDto.setStartTime(Instant.now().with(new TheBeginOfDayAdjuster()));
        taskDto.setDueTime(Instant.now().with(new TheEndOfDayAdjuster()));

        Schedule schedule = modelMapper.map(taskDto, Schedule.class);

        when(scheduleService.updateSchedule(Mockito.any(Schedule.class)))
                .thenReturn(schedule);

        mockMvc.perform(
                put("/api/task")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(taskDto))
        )
                .andExpect(status().is(200))
                .andDo(this::printResult);
    }

    @Test
    public void testRecycleSchedule() throws Exception {

        Schedule schedule = new Schedule("dubebe");

        when(scheduleService.recycleSchedule(anyString()))
                .thenReturn(schedule);

        mockMvc.perform(
                put("/api/task/{taskId}/recycle", "any")
        )
                .andExpect(status().is(200))
                .andDo(this::printResult);
    }

    @Test
    public void testRestoreSchedule() throws Exception {
        Schedule schedule = new Schedule("dubebe");
        schedule.setDeleted(false);

        when(scheduleService.restoreSchedule(anyString()))
                .thenReturn(schedule);

        mockMvc.perform(
                put("/api/task/{taskId}/restore", "any")
        )
                .andExpect(status().is(200))
                .andDo(this::printResult);
    }

    @Test
    public void testGetCompletedTasks() throws Exception {

        Schedule schedule = new Schedule("dubebe");
        when(scheduleService.getAllSchedulesCompletedForUserFrom(anyString()
                , Mockito.any(Instant.class)
                , Mockito.any(Instant.class)
                , Mockito.anyInt()))
                .thenReturn(Collections.singletonList(schedule));

        mockMvc.perform(
                get("/api/task/completed")
                        .param("from", "")
                        .param("to", Instant.now().toString())
                        .param("limit", "40")
        )
                .andExpect(status().is(200))
                .andDo(this::printResult);
    }


}
