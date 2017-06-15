package org.manlier.providers;

import org.manlier.beans.Project;
import org.manlier.beans.Schedule;
import org.manlier.models.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 清单服务
 * Created by manlier on 2017/6/7.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ProjectService implements org.manlier.providers.interfaces.IProjectService {

    private String inboxPrefix;

    private final ProjectDao projectDao;

    @Autowired
    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Value("inbox_")
    public void setInboxPrefix(String inboxPrefix) {
        this.inboxPrefix = inboxPrefix;
    }

    @Override
    public String getInboxPrefix() {
        return inboxPrefix;
    }

    /**
     * 获得用户的所有的计划
     *
     * @param userId 用户id
     * @return 计划
     */
    @Override
    @Transactional
    public List<Project> getAllProjectForUser(String userId) {
        return projectDao.getAllProjectsForUser(userId);
    }

    /**
     * 通过计划id获取计划
     *
     * @param projectId 计划id
     * @return 计划
     */
    @Override
    @Transactional
    public Project getProjectById(String projectId) {
        return projectDao.getProjectByProjectId(projectId);
    }

    /**
     * 为用户添加计划
     *
     * @param userId  用户id
     * @param project 计划
     * @return 是否添加成功
     */
    @Override
    @Transactional
    public Project addProjectForUser(String userId, Project project) {
        if (projectDao.addProjectForUser(userId, project) != 1)
            return null;
        return project;
    }

    /**
     * 移除计划
     *
     * @param projectId 计划id
     * @return 是否移除成功
     */
    @Override
    @Transactional
    public boolean removeProject(String projectId) {
        return projectDao.deleteProject(projectId) == 1;
    }

    /**
     * 更新计划
     *
     * @param project 计划
     * @return 是否更新成功
     */
    @Override
    @Transactional
    public Project updateProject(Project project) {
        if (projectDao.updateProject(project) != 1)
            return null;
        return project;
    }

    /**
     * 获得当前清单下的所有日程
     *
     * @param projectId 清单id
     * @return 所有日程
     */
    @Override
    @Transactional
    public List<Schedule> getAllSchedulesForProject(String projectId) {
        return projectDao.getAllSchedulesForProject(projectId);
    }
}
