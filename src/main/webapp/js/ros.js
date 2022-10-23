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
	request.open("GET", "/ros/increase/" + id);
	request.send();
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
