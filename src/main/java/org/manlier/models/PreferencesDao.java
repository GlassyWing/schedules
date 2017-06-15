package org.manlier.models;

import org.apache.ibatis.annotations.Param;
import org.manlier.beans.Preferences;

/**
 * 设置数据库访问类
 * Created by manlier on 2017/6/13.
 */
public interface PreferencesDao {

    // 获取设置
    Preferences getPreferencesForUser(@Param("userId") String userId);

    // 更改设置
    int updatePreferences(@Param("preferences") Preferences preferences);
}
