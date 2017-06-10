package org.manlier.controllers;

import org.junit.Before;
import org.junit.Test;
import org.manlier.beans.Schedule;
import org.manlier.providers.interfaces.IScheduleService;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.Arrays;
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

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        Mockito.reset(scheduleService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllSchedule() throws Exception {
        String userId = "97147179147198497";
        Schedule schedule = new Schedule("复习了");
        schedule.setCreateTime(Instant.now());
        List<Schedule> schedules = Arrays.asList(schedule);
        when(scheduleService.getAllSchedulesForUser(userId))
                .thenReturn(schedules);
        mockMvc.perform(
                get("/schedule/all/{userId}", "97147179147198497")
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andDo(mvcResult -> {
            logger.info("response body: " + mvcResult.getResponse().getContentAsString());
        });
    }
}
