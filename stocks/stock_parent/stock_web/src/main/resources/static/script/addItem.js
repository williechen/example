/**
@author willie
@date 2020-08-17
 */

function AddItem(){
  'use strict'
	
  function ajaxAsyncAction(url, data, sccess, fail){
    fetch(url, {
            method: 'POST', // or 'PUT'
            body: JSON.stringify(data), // data can be `string` or {object}!
            headers: new Headers({
              'Content-Type': 'application/json'
            })
          }
    ).then(function (res){
           return res.json();
          }
    ).catch(function(error){
	        console.error('Error:', error);
            fail();
          }
    ).then(function(response){
	        console.log('Success:', response);
            sccess(response);
          }
    );
  }
	
  var InputRadioItem = function(elementId, inputName, data, line=false, inputValues=null){
        
    let sccess = function(response){

	  let parentEl = document.getElementById(elementId);
      parentEl.innerHTML = '';

	  if (response.length > 0){
		for (let i=0; i<response.length;i++){
			let item = response[i];
			
			let inputRadio = document.createElement('input');
			inputRadio.type = 'radio';
			inputRadio.name = inputName;
			inputRadio.id = inputName+"_"+item.code;
			inputRadio.value = item.code;
			
			if (inputValues && inputValues.length > 0){
				for (let j=0;j<inputValues.length;j++){
					let val = inputValues[j];
					if (val == item.code){
						inputRadio.checked = true;
					}
				}
			}
			
			parentEl.appendChild(inputRadio);
			
			let inputValue = document.createElement('span');
			inputValue.innerHTML = item.name;
			
			parentEl.appendChild(inputValue);
			
			if (line){
			  parentEl.appendChild(document.createElement('br'));
			}
		}
	  }
    };

    let fail = function(response){
	  let parentEl = document.getElementById(elementId);
      parentEl.innerHTML = '';
    }

    ajaxAsyncAction("/ajax/getRadioData", data, sccess, fail);
  }

  var InputCheckboxItem = function(elementId, inputName, data, line=false, inputValues=null){
	
	let sccess = function(response){

	  let parentEl = document.getElementById(elementId);
      parentEl.innerHTML = '';

	  if (response.length > 0){
		for (let i=0; i<response.length;i++){
			let item = response[i];
			
			let inputCheckbox = document.createElement('input');
			inputCheckbox.type = 'checkbox';
			inputCheckbox.name = inputName;
			inputCheckbox.id = inputName+"_"+item.code;
			inputCheckbox.value = item.code;
			
			if (inputValues && inputValues.length > 0){
				for (let j=0;j<inputValues.length;j++){
					let val = inputValues[j];
					if (val == item.code){
						inputCheckbox.checked = true;
					}
				}
			}
			
			parentEl.appendChild(inputCheckbox);
			
			let inputValue = document.createElement('span');
			inputValue.innerHTML = item.name;
			
			parentEl.appendChild(inputValue);
			
			if (line){
			  parentEl.appendChild(document.createElement('br'));
			}
			
		}		
	  }
    }

    let fail = function(response){
	  let parentEl = document.getElementById(elementId);
      parentEl.innerHTML = '';
    }

    ajaxAsyncAction("/ajax/getRadioData", data, sccess, fail);
  }

  var selectItem = function(elementId, data, inputValues=null){
	
	let sccess = function(response){
	  
	  let selectEl = document.getElementById(elementId);
      selectEl.innerHTML = '';

      let optionE = document.createElement('option');
	  optionE.value = "";
	  optionE.innerHTML = "請選擇";
			
	  selectEl.appendChild(optionE);

	  if (response.length > 0){
		for (let i=0; i<response.length;i++){
			let item = response[i];
			
			let option = document.createElement('option');
			option.value = item.code;
			option.innerHTML = item.name;
			
			if (inputValues && inputValues.length > 0){
				for (let j=0;j<inputValues.length;j++){
					let val = inputValues[j];
					if (val == item.code){
						option.selected = true;
					}
				}
			}
			
			selectEl.appendChild(option);
		}
	  }
    }

    let fail = function(response){
	  let selectEl = document.getElementById(elementId);
      selectEl.innerHTML = '';

      let optionE = document.createElement('option');
	  optionE.value = "";
	  optionE.innerHTML = "請選擇";
			
	  selectEl.appendChild(optionE);
    }

    ajaxAsyncAction("/ajax/getRadioData", data, sccess, fail);
  }

  /*
      * 把半形字串轉成全形
   * @return 全形字串
   */
  var halfToFull = function (input) {
    let temp = "";
    for (let i = 0; i < input.length; i++) {
        let charCode = input.charCodeAt(i);
        if (charCode <= 126 && charCode >= 33) {
            charCode += 65248;
        } else if (charCode == 32) { // 半形空白轉全形
            charCode = 12288;
        }
        temp = temp + String.fromCharCode(charCode);
    }
    return temp;
  }
   

  return {
	InputRadioItem,
	InputCheckboxItem,
	selectItem,
	
	halfToFull
	 
  }

}