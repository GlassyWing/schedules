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
@CrossOrigin(origins = {"http://localhost:3000"})
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
        logger.info("User {} try to login at {}.", session.getId(), zoneId);
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

        // 检查账号是否已经存在
        if (userService.isAccountExists(user.getEmail())) {
            logger.info("Account has exist!");
            return new BaseResult<>(FAIL, EMAIL_HAS_EXIST.getMsg(), null);
        }
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
     * 修改用户昵称
     *
     * @param userProfileDto 用户概要
     * @return 用户概要（修改后）
     */
    @RequestMapping(value = "/nickname", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<UserProfileDto> changeNickname(@RequestBody() UserProfileDto userProfileDto) {
        User newUser = convertToEntity(userProfileDto);
        logger.info("Try to update user {}", newUser);

        newUser = userService.updateUser(newUser);

        if (newUser != null) {
            logger.info("Update user {} success", newUser);
            return success(convertToDto(newUser));
        }
        logger.info("Update user {} fail", newUser);
        return fail(convertToDto(newUser));
    }

    /**
     * 更改密码
     *
     * @param userProfileDto 用户
     * @return 更改结果
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<UserProfileDto> changePassword(@RequestBody() UserProfileDto userProfileDto, HttpSession session) {
        User newUser = convertToEntity(userProfileDto);
        User oldUser = userService.getCurrentUser();

        logger.info("New user password {}, newPassword {}", newUser.getPassword(), userProfileDto.getNewPassword());

        logger.info("Old user password {}", oldUser.getPassword());

        // 验证原始密码是否正确
        if (!newUser.getPassword().equals(oldUser.getPassword())) {
            return new BaseResult<>(FAIL, PASSWORD_ERROR.getMsg(), null);
        }

        newUser.setPassword(userProfileDto.getNewPassword());

        logger.info("Try to change the password for user {}.", newUser.getEmail());
        newUser = userService.updateUser(newUser);
        if (newUser != null) {
            logger.info("Change password for user {} success.", newUser.getEmail());
            // 更改后，需更新状态
            session.setAttribute(SysConst.LOGIN_SESSION_KEY, newUser);
            return success(convertToDto(newUser));
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
        logger.info("user profile: {}", userProfileDto);
        User newUser = convertToEntity(userProfileDto);
        User oldUser = userService.getCurrentUser();

        // 验证密码是否正确
        if (!newUser.getPassword().equals(oldUser.getPassword())) {
            return new BaseResult<>(FAIL, PASSWORD_ERROR.getMsg(), null);
        }

        logger.info("Try to change the email for user {}.", newUser.getEmail());
        newUser = userService.updateUser(newUser);
        if (newUser != null) {
            logger.info("Change email for user {} success.", newUser.getEmail());
            return success(convertToDto(newUser));
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
        String userId = userService.getCurrentUser().getUserUuid();
        Preferences preferences = convertToEntity(preferencesDto);
        preferences = preferencesService.updatePreferencesForUser(userId, preferences);
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
