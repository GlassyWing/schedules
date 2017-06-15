package org.manlier.dto;

/**
 * Created by manlier on 2017/6/15.
 */
public class ProjectDto {

    private String projectUuid;

    private String userUuid;

    private String title;

    public ProjectDto(String title) {
        this.title = title;
    }

    public ProjectDto() {
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
}
