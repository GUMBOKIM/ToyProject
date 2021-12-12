package com.union.placeorderAutomation.restcontroller.task.part;

import com.union.placeorderAutomation.dto.task.part.status.PartLogDto;
import com.union.placeorderAutomation.dto.task.part.status.PartOutgoingLogDto;
import com.union.placeorderAutomation.entity.OutcomeLog;
import com.union.placeorderAutomation.service.task.TaskPartStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/part-log")
@RestController
public class PartTaskLogController {

    private final TaskPartStatusService partStatusService;

    @GetMapping("/{companyCode}/{date}")
    public ResponseEntity getPartList(@PathVariable("companyCode") String companyCode, @PathVariable("date") String date) {
        List<PartLogDto> partStatus = partStatusService.createPartStatus(companyCode, date);
        return new ResponseEntity(partStatus, HttpStatus.OK);
    }

    @GetMapping("/outgoing/{companyCode}/{date}")
    public ResponseEntity getOutgoingPartList(@PathVariable("companyCode") String companyCode, @PathVariable("date") String date) {
        List<PartOutgoingLogDto> partStatus = partStatusService.createOutgoingPartList(companyCode, date);
        return new ResponseEntity(partStatus, HttpStatus.OK);
    }


}
