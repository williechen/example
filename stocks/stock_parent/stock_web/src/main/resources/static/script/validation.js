/**
@author willie
@date 2020-08-13
 */

function Validation(){
	
	let elementIds = [];
	let message = [];
	
	var isFalse = function(elementId, msg){
		elementIds.push(elementId);
		message.push(msg);

		let el = document.getElementById(elementId);
		el.style = "border: 1px solid red; background-color: pink;";
	}
	
	var require = function (){
		
	}
	
	var restValid = function(){
		console.log(elementIds);
		for(let i=0; i<elementIds.length; i++){
		  let el = document.getElementById(elementIds[i]);
          let styleStr = el.style;
          console.log(styleStr);
          el.style = styleStr.replace("border: 1px solid red; background-color: pink;", "");
		}
	}
	
	var status = function(){
		if (message.length > 0){
			return true;
		} else {
			return false;
		}
	}
	
	var showMessage = function(){
		alert(message.join('\n'));
	}
	
	return {
		isFalse,
		
		restValid,
		status,
		showMessage
	}
}