/**
 *
 */
// JavaScript for handling guest search and modal functionality
var modal = document.getElementById("guestSearchModal");
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// Handle the guest search functionality
document.getElementById("searchGuestSubmitBtn").onclick = function() {
    // Collect form data
    var gsGuestID = document.getElementById("gsGuestID").value;
    var gsFirstName = document.getElementById("gsFirstName").value;
    var gsLastName = document.getElementById("gsLastName").value;
    var gsbirthDate = document.getElementById("gsbirthDate").value;
    var gsGuestPhone = document.getElementById("gsGuestPhone").value;
    var gsGuestPincode = document.getElementById("gsGuestPincode").value;

    // Perform AJAX request to search for the guest
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/portal/search-guest", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var guests = JSON.parse(xhr.responseText);
			if (guests.length > 0) {
				var tbody = document.getElementById("tbody");
				tbody.innerHTML = "";
				// Loop through each guest and create table rows
				guests.forEach(function(guest){
					var guestJson = JSON.stringify(guest).replace(/'/g, "\\'");
					var row = "<tr onclick='openEditGuestProfile("+guestJson+")'>" +
						"<td>" + guest.guestID + "</td>" +
						"<td>" + guest.firstName + "</td>" +
						"<td>" + guest.lastName + "</td>" +
						"<td>" + guest.birthDate + "</td>" +
						"<td>" + guest.phno + "</td>";
						if(null!=guest.address)
						{
						     row+="<td>" + guest.address.postalcode + "</td>";
						}
						row+="</tr>";
					tbody.innerHTML+=row;
				});
			}
			else {
				alert("no guest found");
			}
        }
    };
    xhr.send(JSON.stringify({
		guestID:gsGuestID,
        firstName: gsFirstName,
        lastName:gsLastName,
        birthDate:gsbirthDate,
        phno: gsGuestPhone,
        guestPincode: gsGuestPincode
    }));
};


function openEditGuestProfile(guest)
{
	// Parse the JSON string back into an object
    var guestObject = JSON.parse(JSON.stringify(guest));
	alert(guestObject);
	  // Populate the form fields with the guest data
    document.getElementById("guestID").value = guestObject.guestID;
    document.getElementById("firstName").value = guestObject.firstName;
    document.getElementById("lastName").value = guestObject.lastName;
    document.getElementById("birthDate").value = guestObject.birthDate;
    document.getElementById("guestPhone").value = guestObject.phno;
    if (guestObject.address) {
        document.getElementById("guestPincode").value = guestObject.address.postalcode;
    }
	modal.style.display = "block";
}


