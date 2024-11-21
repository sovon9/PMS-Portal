 async function modifyRes(row)
  {
  	var cells = row.getElementsByTagName("td");
      
      // Access the individual cell values
      var resID = cells[0].innerText;
      window.location.href = '/portal/modify-res/resID/'+resID;
  }