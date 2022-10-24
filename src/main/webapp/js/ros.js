function plainIncrease(id) {
	let request = new XMLHttpRequest();
	request.onload = plainIncreased;
	request.open("GET", "/ros/increase/" + id);
	request.send();
}

function plainIncreased() {
	if (this.status != 200) {
		console.log(this.status);
	} else {
		let target = document.getElementById('counter');
		console.log(this.responseText);
		target.textContent = +target.textContent + 1;
	}
}

function increase(id) {
	let request = new XMLHttpRequest();
	request.onload = increased;
	request.open("GET", "/ros/increase/" + id);
	request.send();
}

function increased() {
	if (this.status != 200) {
		console.log(this.status);
	} else {
		let counter = document.getElementById('counter');
		counter.textContent = +counter.textContent + 1;

		let item = JSON.parse(this.responseText);

		let qty = document.getElementById('qty-' + item.id);
		qty.textContent = +qty.textContent + 1;

		let tot = document.getElementById('tot-' + item.id);
		tot.textContent = addCurrency(tot.textContent, item.price);

		let total = document.getElementById('total');
		total.textContent = addCurrency(total.textContent, item.price);
	}
}

/**
 * Increase a string representing an amount
 *
 * @param amount the initial amount
 * @param delta the increase
 *
 * @returns the string representing the new amount (in €)
 */
function addCurrency(amount, delta) {
	let value = Number(amount.replace(/[^0-9]+/g, "")) + delta * 100;
	let decimal = value % 100;
	if (decimal < 10) {
		decimal += "0";
	}
	return value / 100 + "," + decimal + " €";	
}

function decrease(id) {
	let request = new XMLHttpRequest();
	request.open("GET", "/ros/decrease/" + id);
	request.send();
}

function remove(id) {
	let request = new XMLHttpRequest();
	request.open("GET", "/ros/reset/" + id);
	request.send();
}
