package ru.kpfu.itis.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public interface DateTimeFormats {
    SimpleDateFormat dateFormatYMDViaHyphen = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormatHMSViaColon = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dateFormatDMYViaDot = new SimpleDateFormat("dd.MM.yyyy");

    DateTimeFormatter localDateFormatDMYViaDot = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter localTimeFormatHMViaColon = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter localDateFormatYMDViaHyphen = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
