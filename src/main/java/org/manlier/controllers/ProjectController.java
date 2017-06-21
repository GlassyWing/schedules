package org.manlier.controllers;

import org.manlier.beans.Project;
import org.manlier.beans.Schedule;
import org.manlier.dto.ProjectDto;
import org.manlier.dto.TaskDto;
import org.manlier.dto.base.BaseResult;
import org.manlier.providers.interfaces.IProjectService;
import org.manlier.providers.interfaces.IUserService;
import org.manlier.utils.EntityToDtoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

import static org.manlier.dto.base.BaseResult.Result.*;
import static org.manlier.dto.base.BaseResult.*;

import static org.manlier.utils.EntityToDtoHelper.*;

/**
 * 计划控制器
 * Created by manlier on 2017/6/12.
 */
@Controller
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/project")
public class ProjectController  {

    private Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Resource
    private IProjectService projectService;

    @Resource
    private IUserService userService;

    /**
     * 删除计划
     * TODO: 验证删除的计划的确属于当前用户
     *
     * @param projectId 计划id
     * @return 删除结果
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    @ResponseBody
    public BaseResult<Void> delete(@PathVariable("projectId") String projectId) {
        logger.info("Try to delete project: {}.", projectId);
        if (projectId.startsWith(projectService.getInboxPrefix()))
            return new BaseResult<>(FAIL, "Can't delete the inbox", null);
        if (projectService.removeProject(projectId)) {
            logger.info("Delete project: {} success.", projectId);
            return success();
        }
        logger.info("Delete project: {} fail.", projectId);
        return fail();
    }

    /**
     * 更新计划
     * TODO: 验证更新的计划的确属于当前用户
     *
     * @param projectDto 计划
     * @return 更新结果
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public BaseResult<ProjectDto> update(@RequestBody ProjectDto projectDto) {
        Project project = convertToEntity(projectDto);
        logger.info("Try to update project to be: {}.", project.getTitle());

        if (project.getProjectUuid() == null)
            return new BaseResult<>(FAIL, "The projectId can not be null!", null);

        if (project.getProjectUuid().startsWith(projectService.getInboxPrefix()))
            return new BaseResult<>(FAIL, "The inbox can not be update", null);

        if ((project = projectService.updateProject(project)) != null) {
            logger.info("Update success");
            return success(convertToDto(project));
        }
        logger.info("Update fail");
        return fail();
    }

    /**
     * 添加计划
     *
     * @param projectDto 计划
     * @return 添加结果
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<ProjectDto> add( @RequestBody ProjectDto projectDto) {
        logger.info("Try to create a new project: '{}'", projectDto.getTitle());
        Project project = convertToEntity(projectDto);
        String userId = userService.getCurrentUser().getUserUuid();
        if ((project = projectService.addProjectForUser(userId, project)) != null) {
            logger.info("Create the project '{}' success.", project.getTitle());
            return success(convertToDto(project));
        }
        logger.info("Create project fail.");
        return fail();
    }

    /**
     * 获得该清单下的所有日程
     * TODO: 验证计划的确属于当前用户
     *
     * @param projectId 清单id
     * @return 所有日程
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public List<TaskDto> getAllSchedules(@PathVariable("projectId") String projectId) {
        List<Schedule> schedules = projectService.getAllSchedulesForProject(projectId);

        return schedules.stream()
                .map(EntityToDtoHelper::convertToDto)
                .collect(Collectors.toList());
    }


}
