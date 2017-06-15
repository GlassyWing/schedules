package org.manlier.utils;

import org.manlier.beans.Preferences;
import org.manlier.beans.Project;
import org.manlier.beans.Schedule;
import org.manlier.beans.User;
import org.manlier.dto.PreferencesDto;
import org.manlier.dto.ProjectDto;
import org.manlier.dto.TaskDto;
import org.manlier.dto.UserProfileDto;
import org.modelmapper.ModelMapper;


/**
 * 帮助Entity与DTO对象之间的转换
 * Created by manlier on 2017/6/15.
 */
public class EntityToDtoHelper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static TaskDto convertToDto(Schedule schedule) {
        TaskDto taskDto = modelMapper.map(schedule, TaskDto.class);
        taskDto.setSummaryBaseContent();
        return taskDto;
    }

    public static Schedule convertToEntity(TaskDto taskDto) {
        Schedule schedule = modelMapper.map(taskDto, Schedule.class);
        schedule.setAllDay(taskDto.checkIsAllDay());
        return schedule;
    }

    public static Project convertToEntity(ProjectDto projectDao) {
        return modelMapper.map(projectDao, Project.class);
    }

    public static ProjectDto convertToDto(Project project) {
        return modelMapper.map(project, ProjectDto.class);
    }

    public static User convertToEntity(UserProfileDto userProfileDto) {
        return modelMapper.map(userProfileDto, User.class);
    }

    public static UserProfileDto convertToDto(User user) {
        return modelMapper.map(user, UserProfileDto.class);
    }

    public static Preferences convertToEntity(PreferencesDto preferencesDto) {
        return modelMapper.map(preferencesDto, Preferences.class);
    }

    public static PreferencesDto convertToDto(Preferences preferences) {
        return modelMapper.map(preferences, PreferencesDto.class);
    }
}
