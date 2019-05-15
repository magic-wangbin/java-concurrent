package com.util;

import javax.swing.text.DateFormatter;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * 可多线程并发请求.
 */
public class DateFormatUtil {

    public static Format defaultFormat() {
        ThreadLocal<DateFormatter> formatter = ThreadLocal.withInitial(
            () -> new DateFormatter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
        );
        return formatter.get().getFormat();
    }

    public static Format dateFormatter(String strFormat) {
        ThreadLocal<DateFormatter> formatter = ThreadLocal.withInitial(
            () -> new DateFormatter(new SimpleDateFormat(strFormat))
        );
        return formatter.get().getFormat();
    }

}
