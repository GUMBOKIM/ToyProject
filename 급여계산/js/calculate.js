const transFormCommute = function () {
    commuteExcelMemory.forEach((value, key) => {
            let name = key;
            // 저장할 공간
            let transFormList = [];
            let beforeMode = "퇴근"
            let beforeData;
            let date, start, end, holiday, error;

            value.forEach(commute => {
                // 출근 인정 X Case
                // 1. 출근 -> 출근
                // 2. 퇴근 -> 퇴근
                if (beforeMode == "출근" && commute.mode == "출근") {
                    transFormList.push({
                            date: beforeData.date,
                            start: beforeData.time,
                            end: null,
                            holiday: null,
                            error: true
                        }
                    );

                    date = commute.date;
                    start = commute.time;
                    holiday = holidayCheck(date);
                    beforeMode = "출근";

                } else if (beforeMode == "퇴근" && commute.mode == "퇴근") {
                    transFormList.push({
                            date: commute.date,
                            start: null,
                            end: commute.time,
                            holiday: null,
                            error: true
                        }
                    );

                    beforeMode = "퇴근";

                    // 정상 케이스 출근
                } else if (beforeMode == "퇴근" && commute.mode == "출근") {
                    date = commute.date;
                    start = commute.time;
                    holiday = holidayCheck(date);
                    beforeMode = "출근";
                    // 정상 케이스 퇴근
                } else if (beforeMode == "출근" && commute.mode == "퇴근") {
                    if (beforeData.date.getDate() != commute.date.getDate()) {
                        transFormList.push(
                            {
                                date: beforeData.date,
                                start: beforeData.time,
                                end: null,
                                holiday: null,
                                error: true
                            }
                        );
                        let time = commute.date;
                        if (commute)
                            transFormList.push(
                                {
                                    date: commute.date,
                                    start: null,
                                    end: commute.time,
                                    holiday: null,
                                    error: true
                                }
                            );
                    } else {
                        end = commute.time;
                        error = false;
                        transFormList.push({date, start, end, holiday, error});
                    }
                    beforeMode = "퇴근";
                }
                beforeData = commute;
            });

            transCommuteMemory.set(name, transFormList);
        }
    )
}


const holidayCheck = function (date) {
    //주말 여부
    if (date.getDay() == 6 || date.getDay() == 0) {
        return true;
    } else {
        return false;
    }
    //공휴일 여부
    holidayDate.forEach(date => {
        let holidayDate = new Date(date);
        if (holidayDate.getFullYear() == commute.date.getFullYear()
            && holidayDate.getMonth() == commute.date.getMonth()
            && holidayDate.getDate() == commute.date.getDate()) {
            return true;
        }
    });
}

const calculatePayment = function () {
    transCommuteMemory.forEach((value, key) => {
            if (payExcelMemory.has(key)) {
                let pay = payExcelMemory.get(key);

                let resultList = [];
                let dayYN = pay.주간근무;
                let dayStart = pay.주간출근.split(":");
                let dayStartMinutes = parseInt(dayStart[0]) * 60 + parseInt(dayStart[1]);
                let nightYN = pay.야간근무;
                let nightStartMinutes;
                if (nightYN === "Y") {
                    let nightStart = pay.야간출근.split(":");
                    nightStartMinutes = parseInt(nightStart[0]) * 60 + parseInt(nightStart[1]);
                }
                let payPerTime = pay.시급;

                let workDay = 0;
                let errorCount = 0;
                let totalNormalDay = 0;
                let totalHoliday = 0;
                let totalTime = 0;
                let totalDayTime = 0;
                let totalNightTime = 0;
                let totalDayExtendTime = 0;
                let totalNightExtendTime = 0;
                let totalPay = 0;

                // 비과세 식대, 차량유류비, 노동절수당, 특별수당
                let add1 = pay.식대;
                let add2 = pay.차량유류비;
                let add3 = pay.노동절수당;
                let add4 = pay.특별수당;

                value.forEach(work => {
                        let workDate = work.date;
                        if (workDate >= startInputDate && workDate <= endInputDate) {
                            let workKind;
                            let holiday = work.holiday;
                            let error = work.error;
                            let totalWorkTime = 0;
                            let dayTime = 0;
                            let nightTime = 0;
                            let extendDayTime = 0;
                            let extendNightTime = 0;
                            let lateCheck = false;
                            let pay = 0;
                            let workStart = work.start;
                            let workEnd = work.end;

                            // 출퇴근 정상적으로 찍었을때 로직
                            if (error == false) {
                                let workStartMinutes = workStart.getHours() * 60 + workStart.getMinutes();
                                let workEndMinutes = workEnd.getHours() * 60 + workEnd.getMinutes();

                                // 퇴근시간 야간 보정
                                if (workEndMinutes <= 6 * 60) {
                                    workEndMinutes += 24 * 60;
                                }

                                // 근무시간 30분 단위 보정
                                if (!workStartMinutes % 30 == 0) {
                                    workStartMinutes = workStartMinutes + 30 - (workStartMinutes % 30);
                                }
                                if (!workEndMinutes % 30 == 0) {
                                    workEndMinutes = workEndMinutes - (workEndMinutes % 30);
                                }


                                // 주간 야간 체크
                                if (nightYN === "Y") {
                                    if (Math.abs(workStartMinutes - dayStartMinutes) <= Math.abs(workStartMinutes - nightStartMinutes)) {
                                        workKind = "주간";
                                        if (workStartMinutes <= dayStartMinutes) {
                                            workStartMinutes = dayStartMinutes;
                                        } else {
                                            lateCheck = true;
                                        }
                                    } else {
                                        workKind = "야간";
                                        if (workStartMinutes <= nightStartMinutes) {
                                            workStartMinutes = nightStartMinutes;
                                        } else {
                                            lateCheck = true;
                                        }
                                    }
                                } else {
                                    workKind = "주간"
                                    if (workStartMinutes <= dayStartMinutes) {
                                        workStartMinutes = dayStartMinutes;
                                    } else {
                                        lateCheck = true;
                                    }
                                }

                                // 일한 시간 계산 (휴게시간 모두 주간임)
                                totalWorkTime = (workEndMinutes - workStartMinutes) / 60;
                                if ( totalWorkTime >= 4.5 && totalWorkTime < 9) {
                                    totalWorkTime -= 0.5;
                                } else if (totalWorkTime >= 9) {
                                    totalWorkTime -= 1;
                                } else if (totalWorkTime < 0){
                                    totalWorkTime = 0
                                }

                                // 주간 시간 계산
                                // 1. 모든 업무 시간 주간
                                if ((22 * 60 - workStartMinutes) >= (totalWorkTime + 1) * 60) {
                                    // 1. 연장근무 있을 때
                                    // 2. 연장근무 없을때
                                    if (totalWorkTime > 8) {
                                        dayTime = 8;
                                        extendDayTime = totalWorkTime - 8;
                                    } else {
                                        dayTime = totalWorkTime;
                                    }
                                    // 2. 업무 시간 야간 존재
                                } else {
                                    dayTime = (22 * 60 - workStartMinutes) / 60 - 1
                                    // 1. 연장근무 있을 때
                                    // 2. 연장근무 없을 때
                                    if (totalWorkTime > 8) {
                                        // 1. 연장근무 주간 + 야간
                                        // 2. 연장근무 야간
                                        let extendStartMinutes = workStartMinutes + 9 * 60;
                                        if (extendStartMinutes < 22 * 60 && workEndMinutes > 22 * 60) {
                                            extendDayTime = (22 * 60 - extendStartMinutes) / 60;
                                        }
                                        extendNightTime = (workEndMinutes - 22 * 60) / 60;

                                    } else {
                                        nightTime = totalWorkTime - dayTime - extendDayTime - extendNightTime;
                                    }
                                }
                            }

                            if (!holiday) {
                                totalNormalDay += 1;
                                pay = (dayTime + nightTime * 1.5 + extendDayTime * 1.5 + extendNightTime * 1.5 * 1.5) * payPerTime * 6 / 5;
                            } else {
                                totalHoliday += 1;
                                pay = (dayTime + nightTime * 1.5 + extendDayTime * 1.5 + extendNightTime * 1.5 * 1.5) * payPerTime * 1.5 * 6 / 5;
                            }

                            resultList.push({
                                workDate,
                                workKind,
                                holiday,
                                error,
                                lateCheck,
                                workStart,
                                workEnd,
                                totalWorkTime,
                                dayTime,
                                nightTime,
                                extendDayTime,
                                extendNightTime,
                                pay
                            });

                            errorCount += 1;
                            workDay += 1;
                            totalTime += totalWorkTime;
                            totalDayTime += dayTime;
                            totalNightTime += nightTime;
                            totalDayExtendTime += extendDayTime;
                            totalNightExtendTime += extendNightTime;
                            totalPay += pay;

                        }
                    }
                )
                resultMemory.set(key, {
                    workDay,
                    errorCount,
                    totalNormalDay,
                    totalHoliday,
                    totalTime,
                    totalDayTime,
                    totalNightTime,
                    totalDayExtendTime,
                    totalNightExtendTime,
                    payPerTime,
                    totalPay,
                    // 비과세
                    add1,
                    add2,
                    add3,
                    add4,
                    // 근무목록
                    resultList
                });
            }
        }
    )
}