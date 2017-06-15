package org.manlier.config;

import org.manlier.providers.*;
import org.manlier.providers.interfaces.*;
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
    public INotificationService notificationService() {
        return Mockito.mock(INotificationService.class);
    }

    @Bean
    public IPreferencesService settingsService() {
        return Mockito.mock(PreferencesService.class);
    }

    @Bean
    public IUserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public IScheduleService scheduleService() {
        return Mockito.mock(IScheduleService.class);
    }

    @Bean
    public IProjectService projectService() {
        return Mockito.mock(IProjectService.class);
    }
}

