package org.cwgy.stock.core.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtils {
	
	private static final ZoneId UTC = ZoneId.of("UTC");
	private static final ZoneId TAIWAN = ZoneId.of("Asia/Taipei");
	
	/**
	 * 取得標準系統當前日期時間
	 * @return 當前日期時間
	 */
    public static ZonedDateTime getSystemDate() {
    	return ZonedDateTime.now(UTC);
    }
	
    /**
     * 取得台灣系統當前日期時間
     * @return 當前日期時間
     */
	public static ZonedDateTime getTwSystemDate() {
		return ZonedDateTime.now(TAIWAN);
	}
	
	/**
	 * ZonedDateTime To Date
	 * @param datetime 日期時間
	 * @return 日期時間
	 */
	public static Date toDate(ZonedDateTime datetime) {
		return new Date(datetime.toEpochSecond());
	}
	
	/**
	 * Date To ZonedDateTime UTC
	 * @param date 日期時間
	 * @return 日期時間
	 */
	public static ZonedDateTime toUTCDateTime(Date date) {
		return ZonedDateTime.ofInstant(date.toInstant(), UTC);
	}
	
	/**
	 * Date To ZonedDateTime TAIWAN
	 * @param date 日期時間
	 * @return 日期時間
	 */
	public static ZonedDateTime toTaiwanDateTime(Date date) {
		return ZonedDateTime.ofInstant(date.toInstant(), TAIWAN);
	}
	
	/**
	 * 日期時間加總 UTC
	 * @param y 年
	 * @param m 月
	 * @param d 日
	 * @param h 時
	 * @param i 分
	 * @param s 秒
	 * @return 日期時間
	 */
	public static ZonedDateTime plusUTCDatetime(long y, long m, long d, long h, long i, long s) {
		return ZonedDateTime.now(UTC).plusYears(y).plusMonths(m).plusDays(d).plusHours(h).plusMinutes(i).plusSeconds(s);
	}
	
}
