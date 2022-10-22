function increase(id) {
	let request = new XMLHttpRequest();
    request.onload = callback;
	request.open("GET", "/ros/increase/" + id);
	request.send();
}

function callback() {
    if (this.status != 200) {
        console.log(this.status);
    } else {
        let target = document.getElementById('counter');
        target.textContent = +target.textContent + 1;
    }
}