package com.ndl.trustviec.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class DateUtils {
    private static final String SYSTEM_DATE_FORMAT = "dd-MM-yyyy";

    public static String convertDateToString(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(SYSTEM_DATE_FORMAT);
            return sdf.format(date);
        } catch (Exception e) {
            log.error("{}: Exception when convert Date to String with date --> {}", DateUtils.class.getSimpleName(), date);
            return null;
        }
    }

    public static boolean isBeforeToday(LocalDateTime expireAt) {
        return expireAt != null && expireAt.isBefore(LocalDateTime.now());
    }
}
