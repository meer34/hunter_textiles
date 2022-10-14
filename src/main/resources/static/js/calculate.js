function calculateAmount(quantity) {
	let rate = document.getElementsByName("rate")[0].value;

	price = rate * quantity;

	document.getElementById("priceInput").outerHTML =
			`<input type="text" id="priceInput" placeholder="Price" value="` + price + `" disabled="disabled">`;
}

function calculateAllAmounts(rate) {
	var elements = document.getElementsByClassName("quantity");
//	for(var i = 0; i < elements.length; i++) {
//		alert(`#` + elements[i].value);
//	}

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
	quantity = document.getElementById("quantityMeter");
	kg = document.getElementById("quantityBosta");
	price = document.getElementById("priceInput");
	length = $('#rollTable tr').length - 1;

	var table = document.getElementById("rollTable");
	var row = table.insertRow(length + 1);

	row.insertCell(0).innerHTML = length;
	row.insertCell(1).innerHTML = rollNo.value;
	row.insertCell(2).innerHTML = quantity.value;
	
	var i = 3, kgValue='';
	
	if(kg != null) {
		row.insertCell(i++).innerHTML = kg.value;
		kgValue = kg.value;
	}
	
	row.insertCell(i++).innerHTML = price.value;
	
	var stockInParts = rollNo.value + `|~|` + quantity.value + `|~|` + kgValue + `|~|` + price.value;
	row.insertCell(i).innerHTML = `<input type="button" id="deleteStockInRollBtn" class="submit action-button2" value="Delete" >`
			+ `<input type="hidden" name="stockInParts" class="submit action-button2" value="`+ stockInParts +`" >`;

	rollNo.value = ``;
	quantity.value = ``;
	price.value = ``;
	if(kg != null) kg.value = ``;

});

$("#rollTable").on('click', '#deleteStockInRollBtn', function () {
	$(this).closest('tr').remove();
});


//Stock Out
$("#selectRoll").change(function (event) {
	var arr = event.target.value.split("~");

	id = arr[0];
	sortNo = arr[1];
	rollNo = arr[2];
	quantity = arr[3];
	rate = document.getElementById("rate").value;
	price = quantity * rate;

	var stockOutParts = id + `|~|` + price;

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

function populateTable(receivedBy) {
	var table = document.getElementById("rollTable"), i;

	if(receivedBy == 'KG') {
		table.rows[0].insertCell(3).outerHTML = "<th class='table_heading' id='bostaHeader'>Quantity(Bosta)</th>";
		table.rows[1].insertCell(3).outerHTML = "<td><input type='text' id='quantityBosta' class='quantity' placeholder='KG' ></td>";

		for (i = 2; i < table.rows.length; i++) {
			createCell(table.rows[i].insertCell(3), '', null);
		}
	} else if(receivedBy == 'M') {
		if(document.getElementById("bostaHeader") != null) {
			for (i = 0; i < table.rows.length; i++) {
				table.rows[i].deleteCell(3);
			}
		}
	}

}

function createCell(cell, text, style) {
	var div = document.createElement('div'); // create DIV element
	var txt = document.createTextNode(text); // create text node

	if(style != null) {
		div.setAttribute('class', style);        // set DIV class attribute
		div.setAttribute('className', style);    // set DIV class attribute for IE (?!)
	}

	div.appendChild(txt);                    // append text node to the DIV
	cell.appendChild(div);                   // append DIV to the table cell

}


