package org.manlier.providers.interfaces;

import org.manlier.beans.Repetition;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by manlier on 2017/6/14.
 */
public interface IRepetitionService {

    Repetition getRepetitionForSchedule(String scheduleId);

    Repetition setRepetitionForSchedule(String scheduleId, Repetition repetition);

    boolean cancelRepetitionForSchedule(String scheduleId);
}
