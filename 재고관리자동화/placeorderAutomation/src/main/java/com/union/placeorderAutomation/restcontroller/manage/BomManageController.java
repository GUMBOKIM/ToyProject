package com.union.placeorderAutomation.restcontroller.manage;

import com.union.placeorderAutomation.dto.manage.BomDto;
import com.union.placeorderAutomation.dto.manage.BomPartDto;
import com.union.placeorderAutomation.service.manage.BomManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/manage/bom")
@RestController
public class BomManageController {

    private final BomManageService bomManageService;

    @GetMapping("")
    public ResponseEntity getBomList() {
        List<BomDto> bomList = bomManageService.getBomList();
        return new ResponseEntity(bomList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity createBom(@RequestBody Map<String, String> req){
        BomDto bom = bomManageService.createBom(req.get("bwCode"));
        return new ResponseEntity(bom, HttpStatus.OK);
    }

    @PutMapping("/{bomId}")
    public ResponseEntity updateBom(@PathVariable("bomId") Long bomId, @RequestBody Map<String, String> req){
        BomDto bom = bomManageService.updateBom(bomId, req.get("bwCode"));
        return new ResponseEntity(bom, HttpStatus.OK);
    }

    @DeleteMapping("/{bomId}")
    public ResponseEntity deleteBom(@PathVariable("bomId") Long bomId){
        bomManageService.deleteBom(bomId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{bomId}")
    public ResponseEntity getBomPartList(@PathVariable("bomId") Long bomId) {
        List<BomPartDto> bomPartList = bomManageService.getBomPartList(bomId);
        return new ResponseEntity(bomPartList, HttpStatus.OK);
    }

    @PostMapping("/{bomId}")
    public ResponseEntity createBomPart(@PathVariable("bomId") Long bomId, @RequestBody Map<String, Long> req){
        BomPartDto bomPart = bomManageService.createBomPart(bomId, req.get("partId"));
        return new ResponseEntity(bomPart, HttpStatus.OK);
    }

    @DeleteMapping("/{bomId}/{bomPartId}")
    public ResponseEntity deleteBomPart(@PathVariable("bomId") Long bomId, @PathVariable("bomPartId") Long bomPartId){
        bomManageService.deleteBomPart(bomId, bomPartId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/findPart/{partBwCode}")
    public ResponseEntity findPartIdByBwCode(@PathVariable("partBwCode") String partBwCode){
        Long partId = bomManageService.findPartIdByBwCode(partBwCode);
        return new ResponseEntity(partId, HttpStatus.OK);
    }

}
