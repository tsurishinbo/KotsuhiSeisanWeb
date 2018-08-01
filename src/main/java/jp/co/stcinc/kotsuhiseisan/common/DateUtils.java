package jp.co.stcinc.kotsuhiseisan.common;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日付ユーティリティクラス
 */
public class DateUtils {

    /**
     * 今日の日付を取得する
     * @return 今日の日付
     */
    public static Date getToday() {
        TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");
        Calendar c = Calendar.getInstance(tz);
        Date today = org.apache.commons.lang3.time.DateUtils.truncate(c.getTime(), Calendar.DAY_OF_MONTH);
        return today;
    }
}
