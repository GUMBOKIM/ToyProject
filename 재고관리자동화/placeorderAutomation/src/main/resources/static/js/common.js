function removeAllNode(cell) {
    while (cell.hasChildNodes()) {
        cell.removeChild(cell.firstChild);
    }
    cell.remove();
}

function removeChildNode(cell) {
    while (cell.hasChildNodes()) {
        cell.removeChild(cell.firstChild);
    }
}

function findElementValue(id) {
    return document.getElementById(id).value;
}

async function checkOrderHistory(companyCode, plantCode, date, orderSeq) {
    const url = "/api/common/company-order/" + companyCode + "/" + plantCode + "/" + date;
    orderSeq = orderSeq * 1;
    let response = await fetch(url);
    if(response.status == 200) {
        let data = await response.json();
        return data.includes(orderSeq);
    } else {
        alert("입력 오류입니다.");
    }
}