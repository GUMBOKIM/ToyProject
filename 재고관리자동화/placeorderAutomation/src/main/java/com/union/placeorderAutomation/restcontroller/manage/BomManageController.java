package com.union.placeorderAutomation.restcontroller.manage;

import com.union.placeorderAutomation.dto.manage.bom.BomCreateDto;
import com.union.placeorderAutomation.dto.manage.bom.BomPartDto;
import com.union.placeorderAutomation.service.manage.ManageBomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/manage/bom")
@RestController
public class BomManageController {

    private final ManageBomService bomManageService;

    @GetMapping("")
    public ResponseEntity getBomList() {
        List<String> bomList = bomManageService.getBomList();
        return new ResponseEntity(bomList, HttpStatus.OK);
    }

    @PostMapping("/{bomBwCode}")
    public ResponseEntity createBom(@PathVariable("bomBwCode") String bomBwCode){
        bomManageService.createBom(bomBwCode);
        return new ResponseEntity(HttpStatus.OK);
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

    @PostMapping("")
    public ResponseEntity createBomPart(@RequestBody BomCreateDto bomCreateDto){
        BomPartDto bomPart = bomManageService.createBomPart(bomCreateDto);
        return new ResponseEntity(bomPart, HttpStatus.OK);
    }

    @DeleteMapping("/{bomBwCode}/{partBwCode}")
    public ResponseEntity deleteBomPart(@PathVariable("bomBwCode") String bomBwCode, @PathVariable("partBwCode") String partBwCode){
        bomManageService.deleteBomPart(bomBwCode, partBwCode);
        return new ResponseEntity(HttpStatus.OK);
    }


}
