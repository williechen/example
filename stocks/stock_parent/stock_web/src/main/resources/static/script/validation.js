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
		if (!(el.value && el.value.length > 0 && trim2(el.value) != "")){
			
			elementIds.push(elementId);
		    message.push("["+fieldName+"]為必填!!");
		
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
		if (el.value && el.value.length > 0 && trim2(el.value) != ""){
			
			let dateItem = df.parseDateString(el.value, dateFormat, isTw);
			if (!df.isExistDate(dateItem)){
			  elementIds.push(elementId);
		      message.push("["+fieldName+"]日期輸入錯誤!!");
		
		      el.classList.add("ValidHasError");
            }
		}
		
	}
	
	
	
	var checkWestDate = function(elementId, fieldName, dateFormat){
		checkDate(elementId, fieldName, dateFormat, false);
	}
	
	var checkTwDate = function(elementId, fieldName, dateFormat){
		checkDate(elementId, fieldName, dateFormat, true);
	}
	
	var restValid = function(){
		message = [];
		for(let i=0; i<elementIds.length; i++){
		  let el = document.getElementById(elementIds[i]);
          el.classList.remove("ValidError");
		}
	}
	
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
		
		isFalse,
		require,
		checkDate,
		checkWestDate,
		checkTwDate,
		
		restValid,
		status,
		showMessage
	}
}