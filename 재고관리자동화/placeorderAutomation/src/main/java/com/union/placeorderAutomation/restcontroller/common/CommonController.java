package com.union.placeorderAutomation.restcontroller.common;

import com.union.placeorderAutomation.dto.common.CompanyListDto;
import com.union.placeorderAutomation.dto.common.OrderHistoryDto;
import com.union.placeorderAutomation.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/common")
@RestController
public class CommonController {

    private final CommonService commonService;

    @GetMapping("/company")
    public ResponseEntity getCompanyList() {
        List<CompanyListDto> companyList = commonService.getCompanyList();
        return new ResponseEntity(companyList, HttpStatus.OK);
    }

    @PostMapping("/company-order/check")
    public ResponseEntity findOrderHistory(@RequestBody OrderHistoryDto orderHistoryDto) {
        List<Integer> orderList = commonService.findCompanyOrderHistoryList(orderHistoryDto);
        return new ResponseEntity(orderList, HttpStatus.OK);
    }

    @PostMapping("/company-order/save")
    public ResponseEntity saveOrderHistory(@RequestBody OrderHistoryDto orderHistoryDto) {
        commonService.addCompanyOrderHistory(orderHistoryDto);
        return new ResponseEntity(HttpStatus.OK);
    }

}
