    let companyCode;
    let inputColumn = false;

    function setCompanyCode(value){
    companyCode = value;
}

    function findPartByCompanyCode() {
    document.location.href = "/manage/part/part/" + companyCode;
}

    function clickAddButton() {
    let tbody = document.getElementById("tbody");
    let tr = document.createElement("tr");
    tr.id = 'add';
    let td1 = document.createElement("td");
    let inner = ''
    for (let i = 0; i < companyList.length; i++) {
    inner += `<option value="` + companyList[i].companyCode + `">` + companyList[i].companyName + `</option>`
}
    td1.innerHTML = `<select class="custom-select">
                                        <option selected>회사</option>`
    + inner
    + `</select>`;
    let td2 = document.createElement("td");
    td2.innerHTML = `<input type="text" class="form-control">`;
    let td3 = document.createElement("td");
    td3.innerHTML = `<input type="text" class="form-control">`;
    let td4 = document.createElement("td");
    td4.innerHTML = `<input type="text" class="form-control">`;
    let td5 = document.createElement("td");
    td5.innerHTML = `<input type="text" class="form-control">`;
    let td6 = document.createElement("td");
    td6.innerHTML = `<input type="text" class="form-control">`;
    let td7 = document.createElement("td");
    td7.innerHTML = `<input type="text" class="form-control">`;
    let td8 = document.createElement("td");
    td8.innerHTML = `<input type="text" class="form-control">`;
    tr.append(td1, td2, td3, td4, td5, td6, td7, td8);

    let td9 = document.createElement("td");
    let td10 = document.createElement("td");
    td10.innerHTML = `<button type="button" class="btn btn-success btn-sm" onclick="addPart()"><i class="fas fa-check"></i></button>`
    tr.append(td9, td10);
    tbody.insertBefore(tr, tbody.firstChild);
}

    function addPart() {
    let tdList = document.getElementById("add").childNodes;
    let sel = tdList[0].childNodes[0];
    let result = {
    companyCode: sel.options[sel.selectedIndex].value,
    bwCode: tdList[1].childNodes[0].value,
    partName: tdList[2].childNodes[0].value,
    spCode: tdList[3].childNodes[0].value,
    poCode: tdList[4].childNodes[0].value,
    loadAmount: parseInt(tdList[5].childNodes[0].value),
    location: tdList[6].childNodes[0].value,
    standardYn: tdList[7].childNodes[0].value,
}
    console.log(result);

    const url = "/api/manage/part";
    fetch(url, {
    headers: {
    "Content-Type": "application/json",
},
    method: "POST",
    body: JSON.stringify(result)
}).then(
    response => {
    if (response.status == 200) {
    document.location.href = "/manage/part/part"
}
}
    );
}

    function clickModifyButton(input) {
    let tr = input.parentNode.parentNode;
    let bwCode = tr.childNodes[3].innerText;
    removeChildNode(tr);

    tr.id = 'modify';
    let td1 = document.createElement("td");
    let inner = ''
    for (let i = 0; i < companyList.length; i++) {
    inner += `<option value="` + companyList[i].companyCode + `">` + companyList[i].companyName + `</option>`
}
    td1.innerHTML = `<select class="custom-select">
                                        <option selected>회사</option>`
    + inner
    + `</select>`;
    let td2 = document.createElement("td");
    td2.innerText = bwCode;
    let td3 = document.createElement("td");
    td3.innerHTML = `<input type="text" class="form-control">`;
    let td4 = document.createElement("td");
    td4.innerHTML = `<input type="text" class="form-control">`;
    let td5 = document.createElement("td");
    td5.innerHTML = `<input type="text" class="form-control">`;
    let td6 = document.createElement("td");
    td6.innerHTML = `<input type="text" class="form-control">`;
    let td7 = document.createElement("td");
    td7.innerHTML = `<input type="text" class="form-control">`;
    let td8 = document.createElement("td");
    td8.innerHTML = `<input type="text" class="form-control">`;
    tr.append(td1, td2, td3, td4, td5, td6, td7, td8);

    let td9 = document.createElement("td");
    let td10 = document.createElement("td");
    td10.innerHTML = `<button type="button" class="btn btn-success btn-sm" onclick="modifyPart()"><i class="fas fa-check"></i></button>`
    tr.append(td9, td10);

}

    function modifyPart() {
    let tdList = document.getElementById("modify").childNodes;
    console.log(tdList);
    let sel = tdList[0].childNodes[0];
    let result = {
    companyCode: sel.options[sel.selectedIndex].value,
    bwCode : tdList[1].innerText,
    partName: tdList[2].childNodes[0].value,
    spCode: tdList[3].childNodes[0].value,
    poCode: tdList[4].childNodes[0].value,
    loadAmount: parseInt(tdList[5].childNodes[0].value),
    location: tdList[6].childNodes[0].value,
    standardYn: tdList[7].childNodes[0].value,
}
    const url = "/api/manage/part";
    fetch(url, {
    headers: {
    "Content-Type": "application/json",
},
    method: "PUT",
    body: JSON.stringify(result)
}).then(
    response => {
    if (response.status == 200) {
    document.location.href = "/manage/part/part"
}
}
    );
}

    function clickDeleteButton(input) {
    let bwCode = input.parentNode.parentNode.childNodes[3].innerText;
    const url = "/api/manage/part/" + bwCode;
    fetch(url, {
    method: "DELETE",
}).then(
    response => {
    if (response.status == 200) {
    document.location.href = "/manage/part/part"
} else {
    alert('BOM을 먼저 지우고 삭제하세요');
}
}
    );
}