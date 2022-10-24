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
		let item = JSON.parse(this.responseText);

		changeOrder(item, 1);
		changeRecap(1, item.price);
	}
}

function decrease(id) {
	let request = new XMLHttpRequest();
	request.onload = decreased;
	request.open("GET", "/ros/decrease/" + id);
	request.send();
}

function decreased() {
	if (this.status != 200) {
		console.log(this.status);
	} else {
		let item = JSON.parse(this.responseText);

		if (item.quantity == 1) {
			let ord = document.getElementById('ord-' + item.id);
			ord.style.display = 'none';
		} else {
			changeOrder(item, -1);
		}

		changeRecap(-1, item.price);
	}
}

function remove(id) {
	let request = new XMLHttpRequest();
	request.onload = removed;
	request.open("GET", "/ros/reset/" + id);
	request.send();
}

function removed() {
	if (this.status != 200) {
		console.log(this.status);
	} else {
		let item = JSON.parse(this.responseText);

		let ord = document.getElementById('ord-' + item.id);
		ord.style.display = 'none';

		changeRecap(-item.quantity, item.price);
	}
}

/**
 * Change an amount represented as currency
 *
 * @param current the current amount
 * @param delta the increase
 *
 * @returns the string representing the new amount (in €)
 */
function changeAmount(current, delta) {
	let value = Number(current.replace(/[^0-9]+/g, "")) + delta * 100;
	let decimal = value % 100;
	if (decimal < 10) {
		decimal += "0";
	}
	return value / 100 + "," + decimal + " €";
}

function changeRecap(count, price) {
	let counter = document.getElementById('counter');
	counter.textContent = +counter.textContent + count;

	let total = document.getElementById('total');
	total.textContent = changeAmount(total.textContent, count * price);
}

function changeOrder(item, delta) {
	let qty = document.getElementById('qty-' + item.id);
	qty.textContent = +qty.textContent + delta;

	let tot = document.getElementById('tot-' + item.id);
	tot.textContent = changeAmount(tot.textContent, delta * item.price);
}
