package org.manlier.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.manlier.customs.json.Views;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * 用户类
 * Created by manlier on 2017/6/4.
 */
public class User {

    @JsonView(Views.Public.class)
    private String userUuid;

    @JsonView(Views.Public.class)
    private String email;

    @JsonView(Views.Public.class)
    private String nickname;

    @JsonView(Views.Internal.class)
    private String password;

    @JsonView(Views.Public.class)
    private String img;

    @JsonView(Views.Internal.class)
    private Instant createTime;

    @JsonView(Views.Public.class)
    private Preferences preferences;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
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

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public String toString() {
        return "User{" +
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
