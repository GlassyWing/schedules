package org.manlier.customs.temporal;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

/**
 * Created by manlier on 2017/6/10.
 */
public class TheEndOfDayAdjuster implements TemporalAdjuster {
    @Override
    public Temporal adjustInto(Temporal temporal) {
        Instant date = Instant.from(temporal);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());
        date = localDateTime
                .plusDays(1)
                .toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        return temporal.with(date);
    }
}
