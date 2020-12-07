package org.cwgy.stock.core.util;

import java.lang.reflect.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeanUtils {
	Logger log = LogManager.getLogger(getClass());
	
	/**
	 *  檢查物件內容為空
	 * @param clazz 物件項目
	 * @return boolean 是否為空
	 * @throws IllegalAccessException 非法訪問異常
	 */
	public boolean checkBeanFieldNull(Class<?> clazz) throws IllegalAccessException {
	    for (Field f : clazz.getDeclaredFields())
	        if (f.get(clazz) != null)
	            return false;
	    return true;   
	}
	
	

}
