/**
@author willie
@date 2020-08-17
 */

function AddItem(){
  'use strict'
	
  function ajaxSyncAction(url, data, sccess, fail){
		let a = [
			{"code": 1, "name": "t"}, 
			{"code": 2, "name": "e"}, 
			{"code": 3, "name": "s"}, 
			{"code": 4, "name": "t"}, 
			{"code": 5, "name": "?"}
		];
		
		sccess(a);
  }
	
  var InputRadioItem = function(elementId, inputName, line){
        
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

    ajaxSyncAction("", {}, sccess, fail);
  }

  var InputCheckboxItem = function(elementId, inputName, line){
	
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

    ajaxSyncAction("", {}, sccess, fail);
  }

  var selectItem = function(elementId){
	
	let sccess = function(response){
	  
	  let selectEl = document.getElementById(elementId);
      selectEl.innerHTML = '';

      let optionE = document.createElement('option');
	  optionE.value = "";
	  optionE.name = "請選擇";
			
	  selectEl.appendChild(optionE);

	  if (response.length > 0){
		for (let i=0; i<response.length;i++){
			let item = response[i];
			
			let option = document.createElement('option');
			option.value = item.code;
			option.name = item.name;
			
			selectEl.appendChild(option);
		}
	  }
    }

    let fail = function(response){
	  let selectEl = document.getElementById(elementId);
      selectEl.innerHTML = '';

      let optionE = document.createElement('option');
	  optionE.value = "";
	  optionE.name = "請選擇";
			
	  selectEl.appendChild(optionE);
    }

    ajaxSyncAction("", {}, sccess, fail);
  }
   

  return {
	InputRadioItem,
	InputCheckboxItem,
	selectItem
	 
  }

}