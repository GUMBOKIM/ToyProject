const afterCalculateChange = function () {
    let inputContainer = document.getElementById("input-container");
    inputContainer.style.display = "none";
    let resultContainer = document.getElementById("result-container");
    resultContainer.style.display = "";
}

const createResultTable = function () {
    let tbody = document.getElementById("result-tbody");
    let seq = 1;

    resultMemory.forEach((value, key) => {
        let tr = document.createElement("tr");

        let sequence = document.createElement("th");
        sequence.innerText = seq.toString();
        sequence.setAttribute("scope", "row");

        let name = document.createElement("td");
        name.innerText = key;
        let workDay = document.createElement("td");
        workDay.innerText = value.workDay;
        let totalNormalDay = document.createElement("td");
        totalNormalDay.innerText = value.totalNormalDay;
        let totalHoliday = document.createElement("td");
        totalHoliday.innerText = value.totalHoliday;
        let totalTime = document.createElement("td");
        totalTime.innerText = value.totalTime;
        let totalDayTime = document.createElement("td");
        totalDayTime.innerText = value.totalDayTime;
        let totalNightTime = document.createElement("td");
        totalNightTime.innerText = value.totalNightTime;
        let totalDayExtendTime = document.createElement("td");
        totalDayExtendTime.innerText = value.totalDayExtendTime;
        let totalNightExtendTime = document.createElement("td");
        totalNightExtendTime.innerText = value.totalNightExtendTime;
        let payPerTime = document.createElement("td");
        payPerTime.innerText = value.payPerTime;
        let totalPay = document.createElement("td");
        totalPay.innerText = value.totalPay;

        let add1 = document.createElement("td");
        add1.innerText = value.add1;
        let add2 = document.createElement("td");
        add2.innerText = value.add2;
        let add3 = document.createElement("td");
        add3.innerText = value.add3;
        let add4 = document.createElement("td");
        add4.innerText = value.add4;

        tr.append(
            sequence,
            name,
            workDay,
            totalNormalDay,
            totalHoliday,
            totalTime,
            totalDayTime,
            totalNightTime,
            totalDayExtendTime,
            totalNightExtendTime,
            payPerTime,
            totalPay,
            add1,
            add2,
            add3,
            add4
        );

        tbody.append(tr);

        seq += 1;
    })
}

const createDetailTable = function () {
    let tbody = document.getElementById("detail-tbody");

    resultMemory.forEach((value, key) => {
        let workerName = key;

        let h3 = document.createElement("div");
        h3.innerText = workerName;
        tbody.append(h3);

        let detailList = value.resultList;
        detailList.forEach(result => {
            let tr = document.createElement("tr");

            let name = document.createElement("th");
            name.innerText = workerName;
            let workDate = document.createElement("th");
            workDate.innerText = workDateToString(result.workDate);
            let error = document.createElement("th");
            if (result.error) {
                tr.style.backgroundColor = "red";
            }
            error.innerText = result.error;
            let workKind = document.createElement("th");
            workKind.innerText = result.workKind;
            let holiday = document.createElement("th");
            if (result.holiday){
                holiday.style.backgroundColor = "blue";
            }
            holiday.innerText = result.holiday;
            let lateCheck = document.createElement("th");
            if (result.lateCheck){
                lateCheck.style.backgroundColor = "yellow"
            }
            lateCheck.innerText = result.lateCheck;
            let workStart = document.createElement("th");
            if (result.workStart == null) {
                workStart.innerText = "";
            } else {
                workStart.innerText = workTimeToString(result.workStart);
            }
            let workEnd = document.createElement("th");
            if (result.workEnd == null) {
                workEnd.innerText = "";
            } else {
                workEnd.innerText = workTimeToString(result.workEnd);
            }
            let totalWorkTime = document.createElement("th");
            totalWorkTime.innerText = result.totalWorkTime;
            let dayTime = document.createElement("th");
            dayTime.innerText = result.dayTime;
            let nightTime = document.createElement("th");
            nightTime.innerText = result.nightTime;
            let extendDayTime = document.createElement("th");
            extendDayTime.innerText = result.extendDayTime;
            let extendNightTime = document.createElement("th");
            extendNightTime.innerText = result.extendNightTime;
            let pay = document.createElement("th");
            pay.innerText = result.pay;

            tr.append(
                workDate,
                error,
                workKind,
                holiday,
                lateCheck,
                workStart,
                workEnd,
                totalWorkTime,
                dayTime,
                nightTime,
                extendDayTime,
                extendNightTime,
                pay
            )

            tbody.append(tr);
        })

    })
}

const workDateToString = function (date) {
    return date.getFullYear(date) + "년 " + (date.getMonth(date) + 1) + "월 " + date.getDate(date) + "일";
}
const workTimeToString = function (date) {
    return (date.getHours(date)) + "시 " + date.getMinutes(date) + "분";
}