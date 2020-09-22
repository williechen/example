package org.cwgy.stock.core.util;

import java.lang.reflect.Field;

public class BeanUtils {
	
	/**
	 *  檢查物件內容為空
	 * @param clazz 物件項目
	 * @return boolean 是否為空
	 * @throws IllegalAccessException
	 */
	public boolean checkBeanFieldNull(Class clazz) throws IllegalAccessException {
	    for (Field f : clazz.getDeclaredFields())
	        if (f.get(clazz) != null)
	            return false;
	    return true;            
	}
	
	

}
