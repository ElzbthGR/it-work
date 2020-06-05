package ru.kpfu.itis.utils;

import java.time.ZoneOffset;

public interface AppConstants {

    String DATE_PATTERN = "dd.MM.yyyy";
    String DATE_EXAMPLE = "01.01.1970";
    String DATE_YMD_PATTERN = "yyyy-MM-dd";
    String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    String DATETIME_Z_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    String DATETIME_TZ_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX";
    String DATETIME_EXAMPLE = "2019-07-27T08:46:27";
    String TIME_PATTERN = "HH:mm:ss";
    String TIME_EXAMPLE = "HH:mm:ss";
    String TIME_Z_PATTERN = "HH:mm:ssXXX";
    String TIME_Z_EXAMPLE = "HH:mm:ss+03:00";
    String TIMEZONE = "UTC";
    String TIMEZONE_MSK = "UTC+3:00";
    String OFFSET_EXAMPLE = "+03:00";

    String EMAIL_EXAMPLE = "some@mail.ru";

    String VALID_EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    String VALID_PHONE_REGEX = "\\+?[0-9]+";

    ZoneOffset MOSCOW_ZONE_OFFSET = ZoneOffset.of("+03:00");
}
