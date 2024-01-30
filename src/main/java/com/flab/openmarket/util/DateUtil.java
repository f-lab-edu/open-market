package com.flab.openmarket.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
    private static final String UTC_ZONE_ID = "UTC";

    public static LocalDateTime getCurrentUTCDateTime() {
        return LocalDateTime.now(ZoneId.of(UTC_ZONE_ID));
    }
}
