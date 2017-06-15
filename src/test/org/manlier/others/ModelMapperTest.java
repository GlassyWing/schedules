package org.manlier.others;

import org.junit.Test;
import org.manlier.beans.Preferences;
import org.manlier.beans.Schedule;
import org.manlier.beans.User;
import org.manlier.dto.TaskDto;
import org.manlier.dto.UserProfileDto;
import org.modelmapper.ModelMapper;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Created by manlier on 2017/6/14.
 */
public class ModelMapperTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testTaskDtoConvertToSchedule() {
        TaskDto taskDto = new TaskDto("看书", "在图书馆看书去，记住多约几个(✿◕‿◕✿)，哇咔咔");

        Schedule schedule = modelMapper.map(taskDto, Schedule.class);
        schedule.setAllDay(taskDto.checkIsAllDay());
        System.out.println(schedule);
    }

    @Test
    public void testScheduleConvertToTaskDto() {
        Schedule schedule = new Schedule("看书", "在图书馆看书去，记住多约几个(✿◕‿◕✿)，哇咔咔");
        TaskDto taskDto = modelMapper.map(schedule, TaskDto.class);
        taskDto.setSummaryBaseContent();
        System.out.println(taskDto);
    }

    @Test
    public void testUserProfileAndUser() {
        User user = new User("1904910", "19049014");
        Preferences preferences = new Preferences();
        preferences.setTimeZone(ZoneId.of("Asia/Shanghai"));
        preferences.setDailyRemindToggle(true);
        preferences.setDailyRemindTime(LocalTime.of(12, 30));
        preferences.setDefaultRemindBefore(Duration.ofHours(1));

        user.setPreferences(preferences);

        UserProfileDto userProfileDto = modelMapper.map(user, UserProfileDto.class);
        System.out.println(userProfileDto);
    }
}
