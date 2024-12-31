/**
 * 
 */
 
 function paymentTypeValidation(type)
 {
	if(type.value=="CC" || type.value=="DC")
	{
		document.getElementById("cardNumber").disabled=false;
	}
	else
	{
		document.getElementById("cardNumber").disabled=true;
	}
 }
 
 function enableDisbledCCExpiry(ccnum)
 {
	if(!ccnum.value.trim())
	{
		document.getElementById("cardExpiry").disabled=true;
	}
	else
	{
		document.getElementById("cardExpiry").disabled=false;
	}
 }
 
function validateCardNum(event)
{
	const charCode = event.which ? event.which : event.keyCode;
    // Allow only numbers (48-57) and backspace (8)
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

var arriveDate = document.getElementById("arriveDate");
arriveDate.onchange=function()
{
	arriveDateVal=arriveDate.value;
	if(arriveDateVal)
	{
		var selectedDate = new Date(arriveDateVal);
		var nextDay = new Date(selectedDate);
		nextDay.setDate(selectedDate.getDate()+1);
		// Format the next day to YYYY-MM-DD (required for the date input)
        const nextDayFormatted = nextDay.toISOString().split("T")[0];
		document.getElementById("deptDate").min=nextDayFormatted;
	}
};