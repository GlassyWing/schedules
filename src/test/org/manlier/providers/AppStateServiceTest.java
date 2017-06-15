package org.manlier.providers;

import org.junit.Test;
import org.manlier.dto.AppState;
import org.manlier.providers.interfaces.IAppStateService;

import javax.annotation.Resource;

/**
 * Created by manlier on 2017/6/15.
 */
public class AppStateServiceTest extends BaseServiceTest {

    @Resource
    private IAppStateService appStateService;

    private String defaultUserId = "97177576174256135";

    @Test
    public void testGetAppState() {
        AppState appState = appStateService.getAppStateFourUser(defaultUserId);

        System.out.println(appState.getInboxId());
        System.out.println(appState.getProjects().size());
        System.out.println(appState.getTasks().size());
        System.out.println(appState.getUser());
    }
}
