package org.manlier.beans;

/**
 * 清单/计划类
 * Created by manlier on 2017/6/4.
 */
public class Project {

    private String projectUuid;

    private String userUuid;

    // 计划标题
    private String title;

    public Project(String title) {
        this.title = title;
    }

    public Project() {
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectUuid='" + projectUuid + '\'' +
                ", userUuid='" + userUuid + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
