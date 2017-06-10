package org.manlier.providers.listeners;

import org.manlier.beans.Reminder;

import java.util.List;

/**
 * Created by manlier on 2017/6/7.
 */
public interface OnRemindersChangeListener {

    public void onRemindersChange(List<Reminder> newReminders);
}
