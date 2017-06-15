package org.manlier.providers;

import org.manlier.beans.Preferences;
import org.manlier.models.PreferencesDao;
import org.manlier.providers.interfaces.IPreferencesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 设置服务
 * Created by manlier on 2017/6/13.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class PreferencesService implements IPreferencesService {

    @Resource
    private PreferencesDao preferencesDao;

    @Override
    @Transactional
    public Preferences getPreferencesForUser(String userId) {
        return preferencesDao.getPreferencesForUser(userId);
    }

    @Override
    @Transactional
    public Preferences updatePreferencesForUser(String userId, Preferences preferences) {
        preferences.setUserUuid(userId);
        if (preferencesDao.updatePreferences(preferences) != 1)
            return null;
        return preferences;
    }
}
