package org.manlier.providers.interfaces;

import org.manlier.beans.Preferences;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by manlier on 2017/6/13.
 */
public interface IPreferencesService {
    @Transactional
    Preferences getPreferencesForUser(String userId);

    @Transactional
    Preferences updatePreferencesForUser(String userId, Preferences preferences);
}
