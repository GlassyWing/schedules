package org.manlier.providers;

import org.manlier.beans.Repetition;
import org.manlier.models.RepetitionDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by manlier on 2017/6/14.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class RepetitionService implements org.manlier.providers.interfaces.IRepetitionService {

    @Resource
    private RepetitionDao repetitionDao;

    @Override
    @Transactional
    public Repetition getRepetitionForSchedule(String scheduleId) {
        return repetitionDao.getRepetitionByScheduleId(scheduleId);
    }

    @Override
    @Transactional
    public Repetition setRepetitionForSchedule(String scheduleId, Repetition repetition) {
        Repetition repetition1 = repetitionDao.getRepetitionByScheduleId(scheduleId);
        if (repetition1 != null) {
            if (repetitionDao
                    .updateRepetitionForSchedule(scheduleId, repetition) == 1) {
                return repetition;
            } else {
                return null;
            }
        }

        if (repetitionDao.addRepetitionForSchedule(scheduleId, repetition) == 1) {
            return repetition;
        }

        return null;
    }

    @Override
    @Transactional
    public boolean cancelRepetitionForSchedule(String scheduleId) {
        return repetitionDao.deleteRepetition(scheduleId) == 1;
    }

}
