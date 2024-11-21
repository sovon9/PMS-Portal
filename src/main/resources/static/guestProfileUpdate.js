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
/*document.getElementById("searchGuestSubmitBtn").onclick = function() {
	alert("calling");
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
}; */

document.getElementById("searchGuestSubmitBtn").onclick = async function() {
	// Collect form data
    var gsGuestID = document.getElementById("gsGuestID").value;
    var gsFirstName = document.getElementById("gsFirstName").value;
    var gsLastName = document.getElementById("gsLastName").value;
    var gsbirthDate = document.getElementById("gsbirthDate").value;
    var gsGuestPhone = document.getElementById("gsGuestPhone").value;
    var gsGuestPincode = document.getElementById("gsGuestPincode").value;
    
	var response = await fetch("/portal/search-guest",{
		method : "POST",
		headers : {"Content-Type":"application/json"},
		body: JSON.stringify({
		guestID:gsGuestID,
        firstName: gsFirstName,
        lastName:gsLastName,
        birthDate:gsbirthDate,
        phno: gsGuestPhone,
        guestPincode: gsGuestPincode
        }),
	});
	if(response.ok)
	{
		var guests = await response.json();
		if (guests.length === 0) {
			resetResponse();
			document.getElementById("error").innerText="No Guests Found";
		  }
		else
		{
			resetResponse();
			document.getElementById("success").innerText="Guest Profile Found : "+guests.length;
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
	}
};

function openEditGuestProfile(guest)
{
	// Parse the JSON string back into an object
    var guestObject = JSON.parse(JSON.stringify(guest));
	// Populate the form fields with the guest data
    document.getElementById("guestID").value = guestObject.guestID;
    document.getElementById("firstName").value = guestObject.firstName;
    document.getElementById("lastName").value = guestObject.lastName;
    if(guestObject.birthDate)
    {
        document.getElementById("birthDate").value = guestObject.birthDate;
    }
    if(guestObject.phno)
    {
    	document.getElementById("phno").value = guestObject.phno;
    }
    if (guestObject.address) {
        document.getElementById("guestPincode").value = guestObject.address.postalcode;
    }
	modal.style.display = "block";
}

function resetResponse()
{
	document.getElementById("error").innerText="";
	document.getElementById("success").innerText="";
}
