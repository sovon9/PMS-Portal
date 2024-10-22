/**
 *
 */
// JavaScript for handling guest search and modal functionality

// Get the modal and button elements
var modal = document.getElementById("guestSearchModal");
var btn = document.getElementById("searchGuestBtn");
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
}

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
			var resultsContainer = document.getElementById("searchResults");
			resultsContainer.innerHTML = ""; // Clear previous results
			
			if (guests.length > 0) {
					var guestTable = document.createElement("table"); // create table
					guestTable.className = "results-table";

					// Create table header
					var headerRow = guestTable.insertRow();
					var headers = [ "Guest ID", "First Name", "Last Name", "BirthDate","Phone Number","Pincode"];
                
                	// populate header content
					headers.forEach(function(header) {
						var th = document.createElement("th");
						th.textContent = header;
						headerRow.appendChild(th);
					});
                
					// Populate table with guest data
					guests.forEach(function(guest) {
						var row = guestTable.insertRow();

						var idCell = row.insertCell(0);
						idCell.textContent = guest.guestID;
						
						var firstNameCell = row.insertCell(1);
						firstNameCell.textContent = guest.firstName;

						var lastNameCell = row.insertCell(2);
						lastNameCell.textContent = guest.lastName;
						
						var birthDateCell = row.insertCell(2);
						birthDateCell.textContent = guest.birthDate;

						var phoneCell = row.insertCell(3);
						phoneCell.textContent = guest.phno;
                    
                    // make the row clickable
					row.onclick = function() {
						document.getElementById("guestID").value = guest.guestID;
						modal.style.display = "none";
					};
				});
				// Append the table to the results container
                resultsContainer.appendChild(guestTable);
			}
			else {
				resultsContainer.textContent = "No guests found. A new guest ID will be generated.";
				//document.getElementById("guestID").value = generateGuestID();
			}
            /*if (response.guestID) {
                // Populate the guestID field with the found guestID
                document.getElementById("guestID").value = response.guestID;
            } else {
                // Auto-generate and populate a new guestID
                document.getElementById("guestID").value = generateGuestID();
            }*/
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

// Function to auto-generate a guestID (can be customized)
var rsModal = document.getElementById("roomSearchModal");
var btn1 = document.getElementById("roomnum");
var span1 = document.getElementsByClassName("rsClose")[0];

// When the user clicks the button, open the modal 
btn1.onclick = function() {
    rsModal.style.display = "block";
    fetchRoomsBasedOnRatePlan();
}

// When the user clicks on <span> (x), close the modal
span1.onclick = function() {
    rsModal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        rsModal.style.display = "none";
    }
}

function fetchRoomsBasedOnRatePlan() {
    var ratePlan = document.getElementById("ratePlan").value;

    if (ratePlan) {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/portal/get-rooms-by-rateplan?ratePlan=" + encodeURIComponent(ratePlan), true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var rooms = JSON.parse(xhr.responseText);
                var tbody = document.querySelector("#roomSearchModal tbody");
                tbody.innerHTML = ""; // Clear existing rows

                rooms.forEach(function (room) {
                   var row = document.createElement("tr");
                    var cell = document.createElement("td");
                    cell.textContent = room;
                    cell.onclick = function() {
                        selectRoom(room);
                    };
                    row.appendChild(cell);
                    tbody.appendChild(row);
                });

                // Open the modal
                document.getElementById("roomSearchModal").style.display = "block";
            }
        };
        xhr.send();
    }
}

function selectRoom(roomNumber) {
    document.getElementById("roomnum").value = roomNumber;
    document.getElementById("roomSearchModal").style.display = "none"; // Close the modal after selection
}
