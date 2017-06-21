package org.manlier.controllers;

import org.manlier.beans.User;
import org.manlier.contracts.SysConst;
import org.manlier.dto.AppState;
import org.manlier.dto.base.BaseResult;
import org.manlier.providers.interfaces.IAppStateService;
import org.manlier.providers.interfaces.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import static org.manlier.dto.base.BaseResult.*;

/**
 * 应用程序状态控制器
 * Created by manlier on 2017/6/15.
 */
@Controller
@RequestMapping("/api/batch")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AppStateController {

    private Logger logger = LoggerFactory.getLogger(AppStateController.class);

    @Resource
    private IAppStateService appStateService;

    @Resource
    private IUserService userService;

    /**
     * 检出应用程序状态
     *
     * @param checkPoint 检查点
     * @return 应用程序状态
     */
    @RequestMapping(value = "/check/{checkPoint}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<AppState> getCurrentAppState(@PathVariable("checkPoint") long checkPoint) {
        User user = userService.getCurrentUser();
        String userId = user.getUserUuid();
        AppState appState = appStateService.getAppStateFourUser(userId);
        if (appState == null) {
            fail();
        }
        return success(appState);
    }
}
