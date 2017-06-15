package org.manlier.models;

import org.apache.ibatis.annotations.Param;
import org.manlier.beans.Project;
import org.manlier.beans.Schedule;

import java.util.List;

/**
 * Created by manlier on 2017/6/5.
 */
public interface ProjectDao {

    /**
     * 根据清单id查找清单
     *
     * @param projectId 清单id
     * @return 清单
     */
    Project getProjectByProjectId(String projectId);

    /**
     * 添加清单
     *
     * @param userId  用户Id
     * @param project 清单
     * @return 添加数量
     */
    int addProjectForUser(@Param("userId") String userId, @Param("project") Project project);

    /**
     * 更新清单
     *
     * @param project 清单
     * @return 更新数量
     */
    int updateProject(@Param("project") Project project);

    /**
     * 删除清单
     *
     * @param projectId 清单id
     * @return 删除数量
     */
    int deleteProject(@Param("projectId") String projectId);

    /**
     * 查找除指定清单外的所有清单
     *
     * @param userId          用户id
     * @param exceptProjectId 除开的清单id
     * @return 清单
     */
    List<Project> getAllProjectsForUserExcept(@Param("userId") String userId, @Param("exceptProjectId") String exceptProjectId);

    /**
     * 查找用户的所有清单
     *
     * @param userId 用户id
     * @return 清单
     */
    List<Project> getAllProjectsForUser(@Param("userId") String userId);

    /**
     * 获得当前清单下的所有日程
     *
     * @param projectId 清单id
     * @return 所有日程
     */
    List<Schedule> getAllSchedulesForProject(@Param("projectId") String projectId);


}
