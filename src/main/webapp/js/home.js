function increase(id) {
	let request = new XMLHttpRequest();
	request.open("GET", "/ros/increase/" + id);
	request.send();
}