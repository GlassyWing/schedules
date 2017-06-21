package org.manlier.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.manlier.customs.json.Views;

import java.time.Instant;

/**
 * Created by manlier on 2017/6/15.
 */
public class UserProfileDto {

    @JsonView(Views.Public.class)
    private String userUuid;

    @JsonView(Views.Public.class)
    private String email;

    @JsonView(Views.Public.class)
    private String nickname;

    @JsonView(Views.Internal.class)
    private String password;

    @JsonView(Views.Internal.class)
    private String newPassword;

    @JsonView(Views.Public.class)
    private String img;

    @JsonView(Views.Internal.class)
    private Instant createTime;

    @JsonView(Views.Public.class)
    private PreferencesDto preferences;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public PreferencesDto getPreferences() {
        return preferences;
    }

    public void setPreferences(PreferencesDto preferences) {
        this.preferences = preferences;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserProfileDto{" +
                "userUuid='" + userUuid + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", img='" + img + '\'' +
                ", createTime=" + createTime +
                ", preferences=" + preferences +
                '}';
    }
}
