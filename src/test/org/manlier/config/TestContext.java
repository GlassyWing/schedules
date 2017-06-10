package org.manlier.config;

import org.junit.Before;
import org.manlier.models.ScheduleDao;
import org.manlier.providers.ScheduleService;
import org.manlier.providers.interfaces.IScheduleService;
import org.manlier.providers.interfaces.IUserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by manlier on 2017/6/8.
 */
@Configuration
@ImportResource(locations = {
        "classpath:spring/spring-model.xml"
, "classpath:spring/spring-core.xml"})
public class TestContext {

    @Bean
    public IUserService userService() {
        return Mockito.mock(IUserService.class);
    }

    @Bean
    public IScheduleService scheduleService() {
        return Mockito.mock(ScheduleService.class);
    }
}
