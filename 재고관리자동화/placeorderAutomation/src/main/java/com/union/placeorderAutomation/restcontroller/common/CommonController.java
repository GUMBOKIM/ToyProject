package com.union.placeorderAutomation.restcontroller.common;

import com.union.placeorderAutomation.dto.common.CompanyListDto;
import com.union.placeorderAutomation.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/common")
@RestController
public class CommonController {

    private final CommonService commonService;

    @GetMapping("/company")
    public ResponseEntity getCompanyList(){
        List<CompanyListDto> companyList = commonService.getCompanyList();
        return new ResponseEntity(companyList, HttpStatus.OK);
    }

    @GetMapping("/companyOrder/{companyCode}/{plantCode}/{date}")
    public ResponseEntity getOrderHistory(@PathVariable String companyCode,
                                          @PathVariable String plantCode,
                                          @PathVariable String date){
        List<Integer> orderList = commonService.findCompanyOrderHistoryList(companyCode, plantCode, date);
        return new ResponseEntity(orderList, HttpStatus.OK);
    }
}
