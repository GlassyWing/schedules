package org.manlier.controllers;

import org.manlier.dto.AppState;
import org.manlier.providers.interfaces.IAppStateService;
import org.manlier.providers.interfaces.IUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * 应用程序状态控制器
 * Created by manlier on 2017/6/15.
 */
@RequestMapping("/api/batch")
public class AppStateController {

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
    public AppState getCurrentAppState(@PathVariable("checkPoint") long checkPoint) {
        String userId = userService.getCurrentUser().getUserUuid();
        return appStateService.getAppStateFourUser(userId);
    }
}
