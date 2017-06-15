package org.manlier.providers.interfaces;

import org.manlier.beans.Project;
import org.manlier.beans.Schedule;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by manlier on 2017/6/7.
 */
public interface IProjectService {
    String getInboxPrefix();

    @Transactional
    List<Project> getAllProjectForUser(String userId);

    @Transactional
    Project getProjectById(String projectId);

    @Transactional
    Project addProjectForUser(String userId, Project project);

    @Transactional
    boolean removeProject(String projectId);

    @Transactional
    Project updateProject(Project project);

    @Transactional
    List<Schedule> getAllSchedulesForProject(String projectId);
}
