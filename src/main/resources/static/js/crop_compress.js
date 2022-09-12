function calculateAmount(quantity) {
	let rate = document.getElementsByName("rate")[0].value;
	
	price = rate * quantity;

	document.getElementById("priceInput").outerHTML =
			`<input type="text" id="priceInput" placeholder="Price" value="` + price + `" disabled="disabled">`;
}

function calculateAllAmounts(rate) {
	var elements = document.getElementsByClassName("quantity");
	for(var i = 0; i < elements.length; i++) {
	    alert(`#` + elements[i].value);
	}
	
	var table = document.getElementById("stockInPartTable");
	for (var i = 0, row; row = table.rows[i]; i++) {
	   //iterate through rows
	   //rows would be accessed using the "row" variable assigned in the for loop
		
		alert(`Data is` + row.cells[2].innerHTML);
		
	   for (var j = 0, col; col = row.cells[j]; j++) {
	     //iterate through columns
	     //columns would be accessed using the "col" variable assigned in the for loop
	   }
	   
	}
	
}

$("#addStockInPartBtn").click(function () {
	rollNo = document.getElementById("rollNoInput");
	quantity = document.getElementById("quantityInput");
	price = document.getElementById("priceInput");
	length = $('#rollTable tr').length - 1;
	
	var table = document.getElementById("rollTable");
	var row = table.insertRow(length + 1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);
	var cell5 = row.insertCell(4);
	
	cell1.innerHTML = length;
	cell2.innerHTML = rollNo.value;
	cell3.innerHTML = quantity.value;
	cell4.innerHTML = price.value;
	
	var stockInParts = rollNo.value + `|~|` + quantity.value + `|~|` + price.value;
	cell5.innerHTML = `<input type="button" id="deleteStockInRollBtn" class="submit action-button2" value="Delete" >`
					  + `<input type="hidden" name="stockInParts" class="submit action-button2" value="`+ stockInParts +`" >`;
	
	rollNo.value = ``;
	quantity.value = ``;
	price.value = ``;
	
});

$("#rollTable").on('click', '#deleteStockInRollBtn', function () {
	$(this).closest('tr').remove();
});


//Stock Out
$("#selectRoll").change(function (event) {
	var arr = event.target.value.split("~");
	
	sortNo = arr[0];
	rollNo = arr[1];
	quantity = arr[2];
	rate = document.getElementById("rate").value;
	price = quantity * rate;
	
	var stockOutParts = sortNo + `|~|` + rollNo + `|~|` + quantity + `|~|` + price;
	
	length = $('#rollTable tr').length - 1;
	var table = document.getElementById("rollTable");
	var row = table.insertRow(length + 1);
	
	row.insertCell(0).innerHTML = length;
	row.insertCell(1).innerHTML = rollNo;
	row.insertCell(2).innerHTML = quantity;
	row.insertCell(3).innerHTML = price;
	row.insertCell(4).innerHTML = `<input type="button" id="deleteStockOutRollBtn" class="submit action-button2" value="Delete" >`
									+ `<input type="hidden" name="stockOutParts" class="submit action-button2" value="`+ stockOutParts +`" >`;
	
	totalQuantity = document.getElementById("totalQuantity");
	totalQuantity.value = Number(totalQuantity.value) + Number(quantity);
	
	totalPrice = document.getElementById("totalPrice");
	totalPrice.value = Number(totalPrice.value) + Number(price);
	
});


$("#rollTable").on('click', '#deleteStockOutRollBtn', function () {
	var currentRow=$(this).closest("tr"); 
    
    quantity = currentRow.find("td:eq(2)").text();
    price = currentRow.find("td:eq(3)").text();
	
	totalQuantity = document.getElementById("totalQuantity");
	totalQuantity.value = Number(totalQuantity.value) - Number(quantity);
	
	totalPrice = document.getElementById("totalPrice");
	totalPrice.value = Number(totalPrice.value) - Number(price);
    
	$(this).closest('tr').remove();
	
});

