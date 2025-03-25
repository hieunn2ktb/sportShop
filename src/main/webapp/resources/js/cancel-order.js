function toggleCancelReason() {
    var status = document.getElementById("status").value;
    var cancelDiv = document.getElementById("cancelReasonDiv");

    if (status === "CANCEL") {
        cancelDiv.style.display = "block";
    } else {
        cancelDiv.style.display = "none";
    }
}