package org.manlier.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.manlier.beans.Preferences;
import org.manlier.beans.User;
import org.manlier.contracts.SysConst;
import org.manlier.customs.json.Views;
import org.manlier.dto.PreferencesDto;
import org.manlier.dto.UserProfileDto;
import org.manlier.dto.base.BaseResult;
import org.manlier.providers.interfaces.IPreferencesService;
import org.manlier.providers.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.time.ZoneId;

import static org.manlier.dto.base.BaseResult.Result.*;
import static org.manlier.contracts.UserStatus.*;

import static org.manlier.dto.base.BaseResult.*;

import static org.manlier.utils.EntityToDtoHelper.*;

/**
 * 用户控制器
 * Created by manlier on 2017/6/8.
 */
@Controller
@RequestMapping("/api/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    @Resource
    private IPreferencesService preferencesService;

    /**
     * 用户登录
     *
     * @param userProfileDto 用户
     * @param session        会话
     * @return 登录结果
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @JsonView(Views.Public.class)
    @ResponseBody
    public BaseResult<UserProfileDto> login(@RequestBody() UserProfileDto userProfileDto, HttpSession session, ZoneId zoneId) {
        User user = convertToEntity(userProfileDto);
        logger.info("User {} try to login at {}.", user, zoneId);
        if (!userService.isAccountExists(user.getEmail())) {
            logger.info("User email {} not exist", user.getEmail());
            return new BaseResult<>(FAIL, ACCOUNT_NOT_EXIST.getMsg(), null);
        } else if (!userService.isUserExists(user)) {
            logger.info("User password {} not correct.", user.getPassword());
            return new BaseResult<>(FAIL, PASSWORD_ERROR.getMsg(), null);
        } else {
            logger.info("User {} login success.", user.getEmail());
            user = userService.findUserByAccountAndPassword(user.getEmail(), user.getPassword());
            session.setAttribute(SysConst.LOGIN_SESSION_KEY, user);
            return success(convertToDto(user));
        }
    }


    /**
     * 用户注册
     *
     * @param userProfileDto 用户
     * @return 注册结果
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public BaseResult<UserProfileDto> signUp(@RequestBody() UserProfileDto userProfileDto, ZoneId zoneId) {
        User user = convertToEntity(userProfileDto);
        user = userService.addUser(user);
        if (user != null) {

            // 添加用户成功后，设置用户偏好时区为用户注册时的时区
            Preferences preferences = preferencesService
                    .getPreferencesForUser(user.getUserUuid());
            preferences.setTimeZone(zoneId);
            preferences = preferencesService
                    .updatePreferencesForUser(user.getUserUuid(), preferences);
            logger.info("The user {} has added.", user.getEmail());
            user.setPreferences(preferences);
            return success(convertToDto(user));
        }
        return fail();
    }

    /**
     * 用户注销
     *
     * @param session 会话
     * @return 注销结果
     */
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<Void> signOut(HttpSession session) {
        User user = (User) session.getAttribute(SysConst.LOGIN_SESSION_KEY);
        if (user == null) {
            logger.info("User has not login!");
            return new BaseResult<>(FAIL, null);
        }

        session.invalidate();
        logger.info("User {} has sign out", user);
        return new BaseResult<>(SUCCESS, null);
    }

    /**
     * 更改密码
     *
     * @param userProfileDto 用户
     * @return 更改结果
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<UserProfileDto> changePassword(@RequestBody() UserProfileDto userProfileDto) {
        User user = convertToEntity(userProfileDto);
        logger.info("Try to change the password for user {}.", user.getEmail());
        user = userService.updateUser(user);
        if (user != null) {
            logger.info("Change password for user {} success.", user.getEmail());
            return success(convertToDto(user));
        }
        logger.info("Try to change password for user fail.");
        return fail();
    }

    /**
     * 更改邮箱
     *
     * @param userProfileDto 用户
     * @return 更改结果
     */
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<UserProfileDto> email(@RequestBody() UserProfileDto userProfileDto) {
        User user = convertToEntity(userProfileDto);
        logger.info("Try to change the email for user {}.", user.getEmail());
        user = userService.updateUser(user);
        if (user != null) {
            logger.info("Change password for user {} success.", user.getEmail());
            return success(convertToDto(user));
        }
        logger.info("Try to change password for user fail.");
        return fail();
    }

    /**
     * 偏好设置
     *
     * @param preferencesDto 偏好
     * @return 设置结果
     */
    @RequestMapping(value = "/preferences/settings", method = RequestMethod.PUT)
    @ResponseBody
    public BaseResult<PreferencesDto> setPreferences(@RequestBody PreferencesDto preferencesDto) {
        Preferences preferences = convertToEntity(preferencesDto);
        preferences = preferencesService.updatePreferencesForUser(preferencesDto.getUserUuid(), preferences);
        if (preferences != null) {
            return success(convertToDto(preferences));
        }
        return fail();
    }

    /**
     * 获得用户偏好
     *
     * @return 用户偏好设置
     */
    @RequestMapping(value = "/preferences/settings", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<PreferencesDto> getPreferences() {
        String userId = userService.getCurrentUser().getUserUuid();
        logger.info("Try to get user preferences setting");
        Preferences preferences = preferencesService.getPreferencesForUser(userId);
        if (preferences != null) {
            logger.info("Get user preferences success");
            return success(convertToDto(preferences));
        }
        logger.info("There's no preferences exist, please check whether user exist");
        return fail(null);
    }

}
