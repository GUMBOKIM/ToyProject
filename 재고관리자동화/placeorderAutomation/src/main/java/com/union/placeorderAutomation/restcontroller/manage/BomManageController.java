package com.union.placeorderAutomation.restcontroller.manage;

import com.union.placeorderAutomation.dto.manage.BomDto;
import com.union.placeorderAutomation.dto.manage.BomPartDto;
import com.union.placeorderAutomation.service.manage.BomManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/manage/bom")
@RestController
public class BomManageController {

    private final BomManageService bomManageService;

    @GetMapping("")
    public ResponseEntity getBomList() {
        List<String> bomList = bomManageService.getBomList();
        return new ResponseEntity(bomList, HttpStatus.OK);
    }

    @PostMapping("/{bomBwCode}")
    public ResponseEntity createBom(@PathVariable("bomBwCode") String bomBwCode){
        BomDto bom = bomManageService.createBom(bomBwCode);
        return new ResponseEntity(bom, HttpStatus.OK);
    }

    @DeleteMapping("/{bomBwCode}")
    public ResponseEntity deleteBom(@PathVariable("bomBwCode") String bomBwCode){
        bomManageService.deleteBom(bomBwCode);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{bomBwCode}")
    public ResponseEntity getBomPartList(@PathVariable("bomBwCode") String bomBwCode) {
        List<BomPartDto> bomPartList = bomManageService.getBomPartList(bomBwCode);
        return new ResponseEntity(bomPartList, HttpStatus.OK);
    }

    @PostMapping("/{bomBwCode}/{partBwCode}")
    public ResponseEntity createBomPart(@PathVariable("bomBwCode") String bomBwCode, @PathVariable("partBwCode") String partBwCode){
        BomPartDto bomPart = bomManageService.createBomPart(bomBwCode, partBwCode);
        return new ResponseEntity(bomPart, HttpStatus.OK);
    }

    @DeleteMapping("/{bomBwCode}/{partBwCode}")
    public ResponseEntity deleteBomPart(@PathVariable("bomBwCode") String bomBwCode, @PathVariable("partBwCode") String partBwCode){
        bomManageService.deleteBomPart(bomBwCode, partBwCode);
        return new ResponseEntity(HttpStatus.OK);
    }


}
