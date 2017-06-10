package org.manlier.controllers;

import org.manlier.beans.User;
import org.manlier.dto.base.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.manlier.dto.base.BaseResult.Result.*;

/**
 * 用户控制器
 * Created by manlier on 2017/6/8.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户登录
     *
     * @param user    用户
     * @param session 会话
     * @return 登录结果
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Void> login(@RequestBody() User user, HttpSession session) {
        session.setAttribute("account", user.getEmail());
        logger.info("user %s has logged ", user.getEmail());
        return new BaseResult<>(SUCCESS, null);
    }

    /**
     * 用户注销
     *
     * @param session 会话
     * @return 注销结果
     */
    @RequestMapping(value = "/signout/{account}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<Void> signOut(@PathVariable("account") String account, HttpSession session) {
        if (session.getAttribute("account") == null) {
            return new BaseResult<>(FAIL, null);
        }

        session.invalidate();
        logger.info("User %s has sign out", account);
        return new BaseResult<>(SUCCESS, null);
    }


}
