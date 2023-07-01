package org.laban.learning.spring.utils.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Now {
    private Now () {}

    private static final ZoneOffset TZ_OFFSET_UTC = ZoneOffset.UTC;
    private static final Clock UTC_CLOCK = Clock.system(TZ_OFFSET_UTC);


    public static Clock clock() {
        return UTC_CLOCK;
    }

    public static Instant instant() {
        return offsetDateTime().toInstant();
    }

    public static OffsetDateTime offsetDateTime() {
        return OffsetDateTime.now(clock());
    }

    public static OffsetDateTime offsetDateTime(ZoneId zone) {
        return offsetDateTime().atZoneSameInstant(zone).toOffsetDateTime();
    }

    public static OffsetDateTime offsetDateTime(ZoneOffset offset) {
        return offsetDateTime().withOffsetSameInstant(offset);
    }

    public static LocalDateTime localDateTime() {
        return LocalDateTime.now(clock());
    }

    public static LocalDate localDate() {
        return LocalDate.now(clock());
    }

    public static long currentTimeMillis() {
        return clock().millis();
    }

    public static long unixTimestamp() {
        return currentTimeMillis() / 1000;
    }
}
