package org.manlier.providers.interfaces;

import org.manlier.dto.AppState;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by manlier on 2017/6/15.
 */
public interface IAppStateService {
    @Transactional
    AppState getAppStateFourUser(String userId);
}
