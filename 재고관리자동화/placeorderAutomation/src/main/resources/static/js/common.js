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

function addClassName(element, className) {
    element.classList.add(className);
}

function findElementValue(id) {
    return document.getElementById(id).value;
}

function disableElement(id) {
    document.getElementById(id).disabled = true;
}

function visibleElement(id){
    document.getElementById(id).style.visibility = "visible";
}
function invisibleElement(id){
    document.getElementById(id).style.visibility = "hidden";
}

async function checkOrderHistory(orderInfo) {
    const url = "/api/common/company-order/check";
    let response = await fetch(url, {
        headers: {
            "Content-Type": "application/json",
        },
        method: "POST",
        body: JSON.stringify(orderInfo)
    });
    if (response.status == 200) {
        let data = await response.json();
        let result = data.includes(orderInfo.orderSeq);
        if(!result) {
            let message = "납품 차수가 존재 합니다. " + "현재 가능한 차수는 \n";
            for (let i = 0; i < data.length; i++) {
                message += data[i] + "차 \n";
            }
            alert(message);
        }
        return result;
    }
}
