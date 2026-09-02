// Get the Sidenav
var mySidenav = document.getElementById("mySidenav");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidenav, and add overlay effect
function w3_open() {
	if (mySidenav.style.display === 'block') {
		mySidenav.style.display = 'none';
		overlayBg.style.display = "none";
	} else {
		mySidenav.style.display = 'block';
		overlayBg.style.display = "block";
	}
}

// Close the sidenav with the close button
function w3_close() {
	mySidenav.style.display = "none";
	overlayBg.style.display = "none";
}

function myAccFunc(element) {
	var x = document.getElementById("" + element);
	if (x.className.indexOf("w3-show") == -1) {
		x.className += " w3-show";
		x.previousElementSibling.className += " w3-green";
	} else {
		x.className = x.className.replace(" w3-show", "");
		x.previousElementSibling.className = x.previousElementSibling.className
				.replace(" w3-green", "");
	}
}