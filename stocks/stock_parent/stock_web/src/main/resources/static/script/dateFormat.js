/**
@author willie
@date 2020-08-13
 */

function DateFormat(){
	'use strict'
	
	/**
	解析日期格式
	
	format: 對應日期解析格式
	
	return [
		日期簡易格式,
		日期格式對應表
	]
	 */
    var parseDateFormat = function(format){
	  
	  let format_split = format.split('');
	  let format_map = {};
	  
	  let format_src= "";
	  for (let i=0; i<format_split.length;i++){
		  let item = format_split[i];
		  if (format_map.hasOwnProperty(item) && ["y","M","d","h","m","s"].includes(item)){
			  format_map[item][1] = format_map[item][1] + 1;
		  } else {
			  format_map[item] = [];
			  format_map[item].push(i);
			  format_map[item].push(1);
			  
			  format_src += item;
		  }
	  }
	  
	  return [format_src, format_map];
    }

    /**
       字串補零

    return string
    */ 
    var formatZero = function(dObj, maxNum){
	  let value = ""+dObj;
	  while(value.length < maxNum){
		  value = "0"+value;
	  }
	  
	  return value;
    }
	
	/**
	解析日期字串轉日期物件
	
	dateStr: 日期字串
	format: 對應日期解析格式
	isChese: 是否為民國
	
	return array
	 */
	var parseDateString = function(dateStr, format, isTw){
		
      let formatObj = parseDateFormat(format);
      let format_src = formatObj[0];
      let format_map = formatObj[1];

	  let dateItem = [1911, 1, 1, 0, 0, 0];
	  for (let key in format_map){
		  let sIndex = format_map[key][0];
		  let eIndex = format_map[key][0]+format_map[key][1];
		  // y
		  if ("y" == key){
			  if (isTw){
				  dateItem[0] = 1911+parseInt(dateStr.substring(sIndex, eIndex));
			  } else {
				  dateItem[0] = parseInt(dateStr.substring(sIndex, eIndex));
			  }
		  }
		  // M
		  if ("M" == key){
			  dateItem[1] = parseInt(dateStr.substring(sIndex, eIndex))-1;
		  }
		  // d
		  if ("d" == key){
			  dateItem[2] = parseInt(dateStr.substring(sIndex, eIndex));
		  }
		  // h
		  if ("h" == key){
			  dateItem[3] = parseInt(dateStr.substring(sIndex, eIndex));
		  }
		  // m
		  if ("m" == key){
			  dateItem[4] = parseInt(dateStr.substring(sIndex, eIndex));
		  }
		  // s
		  if ("s" == key){
			  dateItem[5] = parseInt(dateStr.substring(sIndex, eIndex));
		  }
	  }

	  return dateItem;
	}
	
    /**
       日期物件應日期格式轉換

	dateObj: 日期物件
	format: 對應日期顯示格式
	isChese: 是否為民國
	
	return string
	 */
	var formatDateString = function(dateObj, format, isTw){
		
	  let formatObj = parseDateFormat(format);
      let format_src = formatObj[0];
      let format_map = formatObj[1];

	  let dateItem = ['1911', '1', '1', '0', '0', '0'];
	  // 判斷日期長度
	  for (let key in format_map){
		  
		  let itemIndex = format_map[key][0];
		  let itemNum = format_map[key][1];
		  
		  // y
		  if ("y" == key){
			  if (isTw){
				  dateItem[0] = formatZero(dateObj.getFullYear()-1911, itemNum);
			  } else {
				  dateItem[0] = formatZero(dateObj.getFullYear(), itemNum);
			  }
		  }
		  // M
		  if ("M" == key){
			  dateItem[1] = formatZero(dateObj.getMonth()+1, itemNum);
		  }
		  // d
		  if ("d" == key){
			  dateItem[2] = formatZero(dateObj.getDate(), itemNum);
		  }
		  // h
		  if ("h" == key){
			  dateItem[3] = formatZero(dateObj.getHours(), itemNum);
		  }
		  // m
		  if ("m" == key){
			  dateItem[4] = formatZero(dateObj.getMinutes(), itemNum);
		  }
		  // s
		  if ("s" == key){
			  dateItem[5] = formatZero(dateObj.getSeconds(), itemNum);
		  }
	  }
	  // 組成日期格式
	  let format_srcs = format_src.split('');
	  let formatStr = "";
	  for (let i=0; i<format_srcs.length; i++){
		 let key = format_srcs[i];
		// y
		  if ("y" == key){
			  formatStr += dateItem[0];
		  }
		  // M
		  else if ("M" == key){
			  formatStr += dateItem[1];
		  }
		  // d
		  else if ("d" == key){
			  formatStr += dateItem[2];
		  }
		  // h
		  else if ("h" == key){
			  formatStr += dateItem[3];
		  }
		  // m
		  else if ("m" == key){
			  formatStr += dateItem[4];
		  }
		  // s
		  else if ("s" == key){
			  formatStr += dateItem[5];
		  }
		  else {
			  formatStr += format_srcs[i];
		  }
	  }
	  
	  return formatStr;
    }

    /**
       檢查日期字串是否不存在

    dateItem: 解析的數字陣列

     */
    var isExistDate = function(dateItem) {

      //列出12個月，每月最大日期限制
      let limitInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

      let theYear = dateItem[0];
      let theMonth = dateItem[1];
      let theDay = dateItem[2];
      let isLeap = new Date(theYear, 1, 29).getDate() === 29; // 是否為閏年?

      if (isLeap) {
        // 若為閏年，最大日期限制改為 29
        limitInMonth[1] = 29;
      }

      let theHours = dateItem[3];
      let theMinutes = dateItem[4];
      let theSeconds = dateItem[5];

      let checkItem = [];
      checkItem.push( ( theMonth >= 1 && theMonth <= 12 ) ? true : false );
      // 比對該日是否超過每個月份最大日期限制
      checkItem.push( ( theDay >= 1 && theDay <= limitInMonth[theMonth] ) ? true : false );
      checkItem.push( ( theHours >= 0 && theHours <= 24 ) ? true : false );
      checkItem.push( ( theMinutes >= 0 && theMinutes <= 60 ) ? true : false );
      checkItem.push( ( theSeconds >= 0 && theSeconds <= 60 ) ? true : false );

      let result = true;
      for (let i=0; i<checkItem.length; i++){
	    if (!checkItem[i]){
		  result = false;
          break;
	    }
      }

    return result;
}


    /**
       西元轉民國

    dateStr: 日期字串
	parseStr: 對應日期解析格式
	formatStr: 對應日期顯示格式
	isChese: 是否為民國
	
	return string
    */
    var toWestToTwDate = function(dateStr, parseStr, formatStr){
	    let dateItem = parseDateString(dateStr, parseStr, false);
        let newDateStr = "";
        if (isExistDate(dateItem)){
	      let dateObj = new Date(dateItem[0], dateItem[1], dateItem[2], dateItem[3], dateItem[4], dateItem[5]);
          newDateStr = formatDateString(dateObj, formatStr, true);
        } else {
	      throw "日期錯誤!!";
        }

        return newDateStr;
    }

    /**
       民國轉西元

    dateStr: 日期字串
	parseStr: 對應日期解析格式
	formatStr: 對應日期顯示格式
	isChese: 是否為民國
    */
    var toTwToWestDate = function(dateStr, parseStr, formatStr){
	    let dateItem = parseDateString(dateStr, parseStr, true);
        let newDateStr = "";
        if (isExistDate(dateItem)){
	      let dateObj = new Date(dateItem[0], dateItem[1], dateItem[2], dateItem[3], dateItem[4], dateItem[5]);
          newDateStr = formatDateString(dateObj, formatStr, false);
        } else {
	      throw "日期錯誤!!";
        }
        
        return newDateStr;
    }
	
	return {
		isExistDate,
		parseDateString,
		formatDateString,
		toWestToTwDate,
		toTwToWestDate
	}
}