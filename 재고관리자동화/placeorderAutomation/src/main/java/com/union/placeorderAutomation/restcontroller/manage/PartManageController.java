package com.union.placeorderAutomation.restcontroller.manage;

import com.union.placeorderAutomation.dto.manage.part.PartReqDto;
import com.union.placeorderAutomation.dto.manage.part.PartResDto;
import com.union.placeorderAutomation.service.manage.PartManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/manage/part")
@RestController
public class PartManageController {

    private final PartManageService partService;

    @GetMapping("")
    public ResponseEntity getPartList() {
        List<PartResDto> partList = partService.getPartList();
        return new ResponseEntity(partList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity postPart(@RequestBody PartReqDto request){
        PartResDto part = partService.createPart(request);
        return new ResponseEntity(part, HttpStatus.OK);
    }

    @PutMapping("/{partId}")
    public ResponseEntity updatePart(@PathVariable("partId") Long partId, @RequestBody PartReqDto request){
        PartResDto part = partService.updatePart(partId, request);
        return new ResponseEntity(part, HttpStatus.OK);
    }

    @DeleteMapping("/{partId}")
    public ResponseEntity deletePart(@PathVariable("partId") Long partId){
        partService.deletePart(partId);
        return new ResponseEntity("삭제 완료", HttpStatus.OK);
    }
}
