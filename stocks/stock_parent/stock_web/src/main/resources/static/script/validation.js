/**
@author willie
@date 2020-08-13
 */

function Validation(){
	
	let elementIds = [];
	let message = [];
	
	// 初始建立錯誤的CSS
	var initObj = function(){
		var css = '.ValidHasError{ border: 1px solid red; }',
        head = document.head || document.getElementsByTagName('head')[0],
        style = document.createElement('style');

        head.appendChild(style);

        style.type = 'text/css';
        if (style.styleSheet){
          // This is required for IE8 and below.
          style.styleSheet.cssText = css;
        } else {
          style.appendChild(document.createTextNode(css));
        }
	}
	
    /** * 清除掉字串前後的空格,包括全形空格。 */ 
    function trim2(input) { 
	  let newValue = "";

	  if (input && input.length > 0){
		
	    let end = input.length; 
        let start = 0; 
        let val = input.split(''); 
      
        while (start < end && ( val[start] == '　' )) {
	      start++; 
        } 
      
        while (start < end && ( val[end - 1] == '　 ' )) {
	       end--; 
        } 
      
        if (start > 0 || end < input.length){
    	    newValue = input.substring(start, end).trim();
        } else {
    	    newValue = input.trim();
        }

	  }

      return  newValue;
    }

	/**
	自行判斷是錯誤的
	
	elementId: 顯示錯誤的項目
	msg: 錯誤的文字
	 */
	var isFalse = function(elementId, msg){
		elementIds.push(elementId);
		message.push(msg);

		let el = document.getElementById(elementId);
		el.classList.add("ValidHasError");
	}
	
	/**
	項目為必填資料
	
	elementId: 檢查項目
	fieldName: 欄位名稱
	
	 */
	var require = function(elementId, fieldName){
		let el = document.getElementById(elementId);
		if ( !( el.value && el.value.length > 0 && trim2(el.value) != "" ) ){
			
			elementIds.push(elementId);
		    message.push("["+fieldName+"]為必填!!");
		
		    el.classList.add("ValidHasError");
		}
	}
	
	/**
	檢查字串長度
	 */
    var checkStrLength = function(elementId, fieldName, minLength, maxLength){
	    let el = document.getElementById(elementId);
		if ( ( el.value && trim2(el.value) != "" ) && 
		    ( minLength > 0 && trim2(el.value).length < minLength ) &&
            ( maxLength > 0 && trim2(el.value).length > maxLength )
        ){
			
			elementIds.push(elementId);
		    message.push("["+fieldName+"]輸入長度須為["+ minLength +"~"+ maxLength +"]!!");
		
		    el.classList.add("ValidHasError");
		}
    }
	
	/**
	檢查字串最大長度
	 */
    var checkMaxLength = function(elementId, fieldName, lengthNum){
	  let el = document.getElementById(elementId);
		if (( el.value && trim2(el.value) != "" ) && 
            ( lengthNum > 0 && trim2(el.value).length > lengthNum )
        ){
			
			elementIds.push(elementId);
		    message.push("["+fieldName+"]輸入超過最大長度["+ lengthNum +"]!!");
		
		    el.classList.add("ValidHasError");
		}
    }

    /**
      檢查字串最小長度
     */
    var checkMinLength = function(elementId, fieldName, lengthNum){
	  let el = document.getElementById(elementId);
		if (( el.value && trim2(el.value) != "" ) && 
		    ( lengthNum > 0 && trim2(el.value).length < lengthNum )
        ){
			
			elementIds.push(elementId);
		    message.push("["+fieldName+"]輸入長度須為["+ lengthNum+"]!!");
		
		    el.classList.add("ValidHasError");
		}
    }
	
	/**
	檢查日期是否正確
	
	elementId: 檢查項目
	fieldName: 欄位名稱
	dateFormat: 日期格式解析
	isTw: 是否為民國年
	
	 */
	var checkDate = function(elementId, fieldName, dateFormat, isTw){
		let df = new DateFormat();
		
		let el = document.getElementById(elementId);
		if ( el.value && el.value.length > 0 && trim2(el.value) != "" ){
			
			let dateItem = df.parseDateString(el.value, dateFormat, isTw);
			if (!df.isExistDate(dateItem)){
			  elementIds.push(elementId);
		      message.push("["+fieldName+"]日期輸入錯誤!!");
		
		      el.classList.add("ValidHasError");
            }
		}
		
	}
	
	/**
	檢查西元日期是否正確
	
	elementId: 檢查項目
	fieldName: 欄位名稱
	dateFormat: 日期格式解析
	
	 */
	var checkWestDate = function(elementId, fieldName, dateFormat){
		checkDate(elementId, fieldName, dateFormat, false);
	}
	
	/**
	檢查民國日期是否正確
	
	elementId: 檢查項目
	fieldName: 欄位名稱
	dateFormat: 日期格式解析
	
	 */
	var checkTwDate = function(elementId, fieldName, dateFormat){
		checkDate(elementId, fieldName, dateFormat, true);
	}
	
	/**
	檢查日期起訖
	
	elementStartId: 檢查起日項目
	elementEndId: 檢查迄日項目
	dateFormat: 日期格式解析
	isTw: 是否為民國年
	
	 */
    var checkDateBlock = function(elementStartId, elementEndId, dateFormat, isTw){
	    let df = new DateFormat();
		
		let startDate = new Date();
		let endDate = new Date();
		
		let elStart = document.getElementById(elementStartId);
		if ( elStart.value && elStart.value.length > 0 && trim2(elStart.value) != "" ){
			
			let dateItem = df.parseDateString(elStart.value, dateFormat, isTw);
			if (df.isExistDate(dateItem)){
				startDate = new Date(dateItem[0], dateItem[1], dateItem[2], dateItem[3], dateItem[4], dateItem[5]);
            } else {
	          elementIds.push(elementStartId);
		      message.push("[起日]日期輸入錯誤!!");
		
		      elStart.classList.add("ValidHasError");
            }
		}
		
		let elEnd = document.getElementById(elementEndId);
		if ( elEnd.value && elEnd.value.length > 0 && trim2(elEnd.value) != "" ){
			
			let dateItem = df.parseDateString(elEnd.value, dateFormat, isTw);
			if (df.isExistDate(dateItem)){
				endDate = new Date(dateItem[0], dateItem[1], dateItem[2], dateItem[3], dateItem[4], dateItem[5]);
            } else {
	          elementIds.push(elementEndId);
		      message.push("[迄日]日期輸入錯誤!!");
		
		      elEnd.classList.add("ValidHasError");
            }
		}
		
		if ( ( endDate.getTime() - startDate.getTime() ) < 0 ){
			elementIds.push(elementStartId);
			elementIds.push(elementEndId);
		      
            message.push("[起日]超過[迄日]!!");

		    elStart.classList.add("ValidHasError");
		    elEnd.classList.add("ValidHasError");
		}
    }

    /**
       檢查數值區間
     */
	var checkNumber = function(elementId, fieldName, minNumber, maxNumber){
		 let el = document.getElementById(elementId);
		if ( ( el.value && trim2(el.value) != "" ) && 
		    ( parseInt(trim2(el.value)) < minNumber ) && 
            ( parseInt(trim2(el.value)) > maxNumber )
        ){
			
			elementIds.push(elementId);
		    message.push("["+fieldName+"]輸入數值須介於["+ minNumber+ "~" + maxNumber+"]!!");
		
		    el.classList.add("ValidHasError");
		}
	}
	
	/**
	檢查數值最大值
	 */
    var checkMaxNumber = function(elementId, fieldName, number){
	   let el = document.getElementById(elementId);
		if (( el.value && trim2(el.value) != "" ) && 
		    ( parseInt(trim2(el.value)) > number )
        ){
			
			elementIds.push(elementId);
		    message.push("["+fieldName+"]輸入數值須小於["+ number+"]!!");
		
		    el.classList.add("ValidHasError");
		}
    }

    /**
       檢查數值最小值
     */
    var checkMinNumber = function(elementId, fieldName, number){
	   let el = document.getElementById(elementId);
		if (( el.value && trim2(el.value) != "" ) && 
		    ( parseInt(trim2(el.value)) < number )
        ){
			
			elementIds.push(elementId);
		    message.push("["+fieldName+"]輸入數值須大於["+ number+"]!!");
		
		    el.classList.add("ValidHasError");
		}
	}
	
	/**
	
	 */
	var restValid = function(){
		message = [];
		for(let i=0; i<elementIds.length; i++){
		  let el = document.getElementById(elementIds[i]);
          el.classList.remove("ValidError");
		}
	}
	
	/**
	
	 */
	var status = function(){
		let toString = "";
		
		for (let i=0; i<message.length; i++){
			if (message[i] && message[i].length > 0){
				toString = toString + message[i] + "\n";
			}
		}

		if (toString != ""){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	
	 */
	var showMessage = function(){
		let toString = "";
		
		for (let i=0; i<message.length; i++){
			if (message[i] && message[i].length > 0){
				toString = toString + message[i] + "\n";
			}
		}
		
		if (toString != ""){
			alert(toString);
		}
	}
	
	return {
		
		initObj,
		restValid,
		status,
		showMessage,
		
		isFalse,
		require,
		checkStrLength,
		checkMaxLength,
		checkMinLength,
		checkDate,
		checkWestDate,
		checkTwDate,
		checkDateBlock,
		checkNumber,
		checkMaxNumber,
		checkMinNumber
		
	}
}