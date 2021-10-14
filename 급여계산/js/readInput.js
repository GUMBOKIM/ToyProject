const readCommuteExcel = async function () {
    let commuteFiles = event.target.files;
    for (let i = 0; i < commuteFiles.length; i++) {
        const reader = new FileReader();
        reader.onload = () => {
            const data = reader.result;
            const workBook = XLSX.read(data, {type: 'binary'})
            workBook.SheetNames.forEach(sheetName => {
                    let rows = XLSX.utils.sheet_to_json(workBook.Sheets[sheetName]);
                    rows.forEach(row => {
                        let name = row.이름;
                        // key 값이 존재하는지 확인
                        if (!commuteExcelMemory.has(name)) {
                            commuteExcelMemory.set(name, []);
                        }
                        let list = commuteExcelMemory.get(name);
                        let date = new Date(row.발생일자);
                        let time = new Date(row.발생일자 + " " + row.발생시각);
                        let mode = row.모드;

                        if (time.getHours() <= 4){
                            date.setDate(date.getDate() - 1);
                        }

                        list.push({
                            date,
                            time,
                            mode
                        });
                        commuteExcelMemory.set(name, list);
                    })
                }
            )
        }
        reader.readAsBinaryString(commuteFiles[i]);
    }

    commuteExcelCheck = true;
}

const readPayInfoExcel = async function () {
    let payFile = event.target.files[0];
    const reader = new FileReader();
    reader.onload = () => {
        const data = reader.result;
        const workBook = XLSX.read(data, {type: 'binary'})
        workBook.SheetNames.forEach(sheetName => {
                let rows = XLSX.utils.sheet_to_json(workBook.Sheets[sheetName]);
                rows.forEach(row => {
                    payExcelMemory.set(row.이름, row);
                })
            }
        )
    }
    reader.readAsBinaryString(payFile);
    payExcelCheck = true;
}


const readStartDate = function () {
    let input = document.getElementById("startDateInput");
    startInputDate = new Date(input.value);
    startDateCheck = true;
}

const readEndDate = function () {
    let input = document.getElementById("endDateInput");
    endInputDate = new Date(input.value);
    endDateCheck = true;
}

// 콘솔 함수
const consoleCommuteExcelMemory = function () {
    console.log(commuteExcelMemory);
}

const consolePayInfoExcelMemory = function () {
    console.log(payExcelMemory);
}

const consoleTransCommute = function () {
    console.log(transCommuteMemory);
}

const consoleResultMemory = function () {
    console.log(resultMemory);
}