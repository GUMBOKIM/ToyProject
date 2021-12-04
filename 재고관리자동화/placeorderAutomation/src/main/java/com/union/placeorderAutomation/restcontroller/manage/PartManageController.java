package com.union.placeorderAutomation.restcontroller.manage;

import com.union.placeorderAutomation.dto.manage.part.PartReqDto;
import com.union.placeorderAutomation.dto.manage.part.PartResDto;
import com.union.placeorderAutomation.service.manage.ManagePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/manage/part")
@RestController
public class PartManageController {

    private final ManagePartService partService;

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

    @PutMapping("")
    public ResponseEntity putPart(@RequestBody PartReqDto request){
        System.out.println("request = " + request);
        partService.modifyPart(request);
        return new ResponseEntity("수정 완료", HttpStatus.OK);
    }
    @DeleteMapping("/{partBwCode}")
    public ResponseEntity deletePart(@PathVariable("partBwCode") String partBwCode){
        partService.deletePart(partBwCode);
        return new ResponseEntity("삭제 완료", HttpStatus.OK);
    }
}
