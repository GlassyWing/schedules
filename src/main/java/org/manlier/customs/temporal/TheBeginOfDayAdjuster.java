package org.manlier.customs.temporal;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

/**
 * Created by manlier on 2017/6/10.
 */
public class TheBeginOfDayAdjuster implements TemporalAdjuster {
    @Override
    public Temporal adjustInto(Temporal temporal) {
        Instant date = Instant.from(temporal);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());
        date = localDateTime
                .toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        return temporal.with(date);
    }
}
