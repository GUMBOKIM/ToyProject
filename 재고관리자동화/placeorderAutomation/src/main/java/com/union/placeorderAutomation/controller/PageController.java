package com.union.placeorderAutomation.controller;

import com.union.placeorderAutomation.dto.manage.PartResDto;
import com.union.placeorderAutomation.service.common.CommonService;
import com.union.placeorderAutomation.service.manage.BomManageService;
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
    private final PartManageService partManageService;
    private final BomManageService bomManageService;

    @RequestMapping("/")
    public String mainPage() {
        return "redirect:task/outgoing-manual";
    }

    @RequestMapping("/task/outgoing")
    public String taskOutgoing() {
        return "task-outgoing";
    }

    @RequestMapping("/task/outgoing-manual")
    public String taskOutgoingManual(Model model) {
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
    public String managePartBom(Model model) {
        commonService.addCompanyList(model);
        model.addAttribute("bomList", bomManageService.getBomList());
        return "manage-part-bom";
    }

    @RequestMapping("/manage/part/bom/{bomCode}")
    public String managePartBom(@PathVariable String bomCode, Model model) {
        commonService.addCompanyList(model);
        return "manage-part-bom";
    }



    @RequestMapping("/manage/part/part")
    public String managePartPart(Model model) {
        commonService.addCompanyList(model);
        List<PartResDto> partList = partManageService.getPartList();
        model.addAttribute("partList", partList);
        return "manage-part-part";
    }

    @RequestMapping("/manage/part/part/{companyCode}")
    public String managePartPartCompanyCode(@PathVariable String companyCode, Model model) {
        commonService.addCompanyList(model);

        List<PartResDto> partList = partManageService.getPartListByCompanyCode(companyCode);
        model.addAttribute("partList", partList);
        return "manage-part-part";
    }
}