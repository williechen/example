package org.cwgy.stock.core.util;

import java.lang.reflect.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BeanUtils {
	private static Logger log = LogManager.getLogger(BeanUtils.class);
	
	/**
	 *  檢查物件內容為空
	 * @param clazz 物件項目
	 * @return boolean 是否為空
	 * @throws IllegalAccessException 非法訪問異常
	 */
	public static boolean checkBeanFieldNull(Class<?> clazz) throws IllegalAccessException {
	    for (Field f : clazz.getDeclaredFields()) {
	        log.debug(f.getName());
	        if (f.get(clazz) != null) {
	            return false;
	        }
	    }
	    return true;  
	}
	
	

}
