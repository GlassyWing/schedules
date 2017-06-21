package org.manlier.providers;

import org.manlier.dto.AppState;
import org.manlier.dto.ProjectDto;
import org.manlier.dto.TaskDto;
import org.manlier.dto.UserProfileDto;
import org.manlier.providers.interfaces.IAppStateService;
import org.manlier.providers.interfaces.IProjectService;
import org.manlier.providers.interfaces.IScheduleService;
import org.manlier.providers.interfaces.IUserService;
import org.manlier.utils.EntityToDtoHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static org.manlier.utils.EntityToDtoHelper.*;

/**
 * 应用程序状态服务
 * Created by manlier on 2017/6/15.
 */
@Service
public class AppStateService implements IAppStateService {

    @Resource
    private IScheduleService scheduleService;

    @Resource
    private IUserService userService;

    @Resource
    private IProjectService projectService;

    /**
     * 获得当前用户的应用程序状态
     *
     * @param userId 用户id
     * @return 应用程序状态
     */
    @Override
    @Transactional
    public AppState getAppStateFourUser(String userId) {
        AppState appState = new AppState(projectService.getInboxPrefix() + userId);
        List<ProjectDto> projects = projectService.getAllProjectsForUserExcept(userId, projectService.getInboxPrefix() + userId)
                .stream().map(EntityToDtoHelper::convertToDto).collect(Collectors.toList());
        appState.setProjects(projects);
        List<TaskDto> tasks = scheduleService.getAllSchedulesForUser(userId)
                .stream().map(EntityToDtoHelper::convertToDto).collect(Collectors.toList());
        appState.setTasks(tasks);
        UserProfileDto user = convertToDto(userService.getUserById(userId));
        appState.setUser(user);
        return appState;
    }

}
