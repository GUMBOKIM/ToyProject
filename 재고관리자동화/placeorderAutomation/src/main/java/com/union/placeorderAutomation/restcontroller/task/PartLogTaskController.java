package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.task.part.PartStatusDto;
import com.union.placeorderAutomation.service.task.PartStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/part-status")
@RestController
public class PartLogTaskController {

    private final PartStatusService partStatusService;

    @GetMapping("/{companyCode}/{ym}")
    public ResponseEntity getPartList(@PathVariable("companyCode") String companyCode, @PathVariable("ym") String ym) {
        List<PartStatusDto> result = partStatusService.createPartStatus(companyCode, ym);
        return new ResponseEntity(result, HttpStatus.OK);
    }


}
