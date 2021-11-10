package com.union.placeorderAutomation.controller;

import com.union.placeorderAutomation.dto.common.CompanyListDto;
import com.union.placeorderAutomation.dto.common.PlantDto;
import com.union.placeorderAutomation.dto.manage.PartResDto;
import com.union.placeorderAutomation.service.common.CommonService;
import com.union.placeorderAutomation.service.manage.PartManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class PageController {

    private final CommonService commonService;

    @RequestMapping("/")
    public String mainPage() {
        return "redirect:/task/outgoing-manual";
    }

    @RequestMapping("/task/outgoing")
    public String taskOutgoing() {
        return "task-outgoing";
    }

    @RequestMapping("/task/outgoing-manual")
    public String taskOutgoingManual(Model model) {
        List<CompanyListDto> companyList = commonService.getCompanyList();
        model.addAttribute("companyList", companyList);
        List<PlantDto> plantList = commonService.getPlantList();
        model.addAttribute("plantList", plantList);
        return "task-outgoing-manual";
    }

    @RequestMapping("/task/part/log")
    public String taskPartLog() {
        return "task-part-log";
    }

    @RequestMapping("/task/part/stock")
    public String taskPartStock() {
        return "task-part-stock";
    }

    @RequestMapping("/manage/part/bom")
    public String managePartBom() {
        return "manage-part-bom";
    }

    private final PartManageService partManageService;

    @RequestMapping("/manage/part/part")
    public String managePartPart(Model model) {
        List<CompanyListDto> companyList = commonService.getCompanyList();
        model.addAttribute("companyList", companyList);
        List<PartResDto> partList = partManageService.getPartList();
        model.addAttribute("partList", partList);
        return "manage-part-part";
    }

    @RequestMapping("/manage/part/part/{companyCode}")
    public String managePartPartCompanyCode(@PathVariable String companyCode, Model model) {
        List<CompanyListDto> companyList = commonService.getCompanyList();
        model.addAttribute("companyList", companyList);
        List<PartResDto> partList = partManageService.getPartListByCompanyCode(companyCode);
        model.addAttribute("partList", partList);
        return "manage-part-part";
    }
}