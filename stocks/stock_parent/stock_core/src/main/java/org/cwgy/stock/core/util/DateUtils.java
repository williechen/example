package org.cwgy.stock.core.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtils {
	
	private static final ZoneId UTC = ZoneId.of("UTC");
	private static final ZoneId TAIWAN = ZoneId.of("Asia/Taipei");
	
	/**
	 * 取得標準系統當前日期時間
	 * @return
	 */
    public static ZonedDateTime getSystemDate() {
    	return ZonedDateTime.now(UTC);
    }
	
    /**
     * 取得台灣系統當前日期時間
     * @return
     */
	public static ZonedDateTime getTwSystemDate() {
		return ZonedDateTime.now(TAIWAN);
	}
	
	/**
	 * ZonedDateTime To Date
	 * @param datetime
	 * @return
	 */
	public static Date toDate(ZonedDateTime datetime) {
		return new Date(datetime.toEpochSecond());
	}
	
	/**
	 * Date To ZonedDateTime UTC
	 * @param date
	 * @return
	 */
	public static ZonedDateTime toUTCDateTime(Date date) {
		return ZonedDateTime.ofInstant(date.toInstant(), UTC);
	}
	
	/**
	 * Date To ZonedDateTime TAIWAN
	 * @param date
	 * @return
	 */
	public static ZonedDateTime toTaiwanDateTime(Date date) {
		return ZonedDateTime.ofInstant(date.toInstant(), TAIWAN);
	}
	
	/**
	 * 日期時間加總 UTC
	 * @param y 
	 * @param m 
	 * @param d 
	 * @param h 
	 * @param i 
	 * @param s 
	 * @return 
	 */
	public static ZonedDateTime plusUTCDatetime(long y, long m, long d, long h, long i, long s) {
		return ZonedDateTime.now(UTC).plusYears(y).plusMonths(m).plusDays(d).plusHours(h).plusMinutes(i).plusSeconds(s);
	}
	
}
