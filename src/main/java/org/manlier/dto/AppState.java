package org.manlier.dto;

import java.util.List;

/**
 * 应用程序状态
 * Created by manlier on 2017/6/15.
 */
public class AppState {

    // 用户所有的计划
    private List<ProjectDto> projects;

    // 所有的任务（不包括已完成和已回收的任务）
    private List<TaskDto> tasks;

    // 用户概要
    private UserProfileDto user;

    // 收集箱id
    private String inboxId;

    public AppState(String inboxId) {
        this.inboxId = inboxId;
    }

    public AppState() {
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    public UserProfileDto getUser() {
        return user;
    }

    public void setUser(UserProfileDto user) {
        this.user = user;
    }

    public String getInboxId() {
        return inboxId;
    }

    public void setInboxId(String inboxId) {
        this.inboxId = inboxId;
    }

    @Override
    public String toString() {
        return "AppState{" +
                "projects=" + projects +
                ", tasks=" + tasks +
                ", user=" + user +
                ", inboxId='" + inboxId + '\'' +
                '}';
    }
}
