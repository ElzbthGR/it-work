package ru.kpfu.itis.utils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class DateTimeUtils {

    private static final long ONE_DAY_IN_NANOS = 24L * 60 * 60 * 1000_000_000;

    public static boolean isIntersectTwoTimeIntervals(Time from1, Time to1, Time from2, Time to2) {
        return from1.getTime() < to2.getTime() && from2.getTime() < to1.getTime();
    }

    public static boolean isIntersectTwoTimeIntervals(LocalTime from1, LocalTime to1, LocalTime from2, LocalTime to2) {
        return from1.isBefore(to2) && from2.isBefore(to1);
    }

    public static boolean isBetweenOrEquals(LocalTime time, LocalTime from, LocalTime to) {
        if (from.equals(to)) {
            return true;
        }
        long nanoFrom = from.toNanoOfDay();
        long nanoCur = (time.toNanoOfDay() + ONE_DAY_IN_NANOS - nanoFrom) % ONE_DAY_IN_NANOS;
        long nanoTo = (to.toNanoOfDay() + ONE_DAY_IN_NANOS - nanoFrom) % ONE_DAY_IN_NANOS;
        return nanoCur <= nanoTo;
    }

    public static boolean isBetweenOrEquals(Time time, Time from, Time to) {
        return isBetweenOrEquals(time.toLocalTime(), from.toLocalTime(), to.toLocalTime());
    }

    public static boolean isBetweenOrEquals(LocalDate date, LocalDate from, LocalDate to) {
        return (date.isAfter(from) || date.equals(from)) && (date.isBefore(to) || date.equals(to));
    }

    public static boolean isBetweenOrEquals(Date date, Date from, Date to) {
        return isBetweenOrEquals(date.toLocalDate(), from.toLocalDate(), to.toLocalDate());
    }

    public static boolean isBetweenOrEquals(LocalDateTime dateTime, LocalDateTime from, LocalDateTime to) {
        return (dateTime.isAfter(from) || dateTime.equals(from)) && (dateTime.isBefore(to) || dateTime.equals(to));
    }

    public static boolean isBetweenOrEquals(Timestamp dateTime, Timestamp from, Timestamp to) {
        return isBetweenOrEquals(dateTime.toLocalDateTime(), from.toLocalDateTime(), to.toLocalDateTime());
    }

    public static LocalDateTime toLocalDateTime(Date date, Time time) {
        if (date == null || time == null) {
            return null;
        }
        return LocalDateTime.of(date.toLocalDate(), time.toLocalTime());
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    public static Timestamp toTimestamp(LocalDateTime dateTime) {
        return dateTime != null ? Timestamp.valueOf(dateTime) : null;
    }

    public static Timestamp toTimestamp(Date date, Time time) {
        if (date == null || time == null) {
            return null;
        }
        return Timestamp.valueOf(toLocalDateTime(date, time));
    }

    public static LocalTime roundDownMinutes(Time time) {
        return time.toLocalTime().withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalTime roundUpMinutes(Time time) {
        LocalTime localTime = time.toLocalTime().withSecond(0).withNano(0);
        if (localTime.getMinute() != 0) {
            localTime = localTime.plusMinutes(60 - localTime.getMinute());
        }
        return localTime;
    }

    public static Long toMillis(LocalDateTime dateTime) {
        return Optional.ofNullable(dateTime)
                .map(dt -> dt.atZone(ZoneId.systemDefault()))
                .map(ZonedDateTime::toInstant)
                .map(Instant::toEpochMilli)
                .orElse(null);
    }

    public static Long toMillis(LocalDate date) {
        return Optional.ofNullable(date)
                .map(dt -> dt.atStartOfDay(ZoneId.systemDefault()))
                .map(ZonedDateTime::toInstant)
                .map(Instant::toEpochMilli)
                .orElse(null);
    }

    public static LocalDateTime toLocalDateTime(final Long millis) {
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
