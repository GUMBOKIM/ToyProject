package com.union.placeorderAutomation.controller;

import com.union.placeorderAutomation.service.common.CommonService;
import com.union.placeorderAutomation.service.manage.ManageBomService;
import com.union.placeorderAutomation.service.manage.ManagePartService;
import com.union.placeorderAutomation.service.task.TaskPartStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class PageController {

    private final CommonService commonService;
    private final ManagePartService partManageService;
    private final TaskPartStockService partStockService;
    private final ManageBomService bomManageService;

    @RequestMapping("/")
    public String mainPage() {
        return "redirect:/task/outgoing-manual";
    }
//
//    @RequestMapping("/task/outgoing")
//    public String taskOutgoing(Model model) {
//        commonService.addCompanyList(model);
//        commonService.addPlantList(model);
//        return "task-outgoing";
//    }
//
//    @RequestMapping("/task/outgoing-select")
//    public String taskOutgoingSelect(Model model) {
//        commonService.addCompanyList(model);
//        commonService.addPlantList(model);
//        return "task-outgoing-select";
//    }

    @RequestMapping("/task/order-history")
    public String orderList(Model model) {
        commonService.addCompanyList(model);
        return "task-order-history";
    }

    @RequestMapping("/task/outgoing-manual")
    public String taskOutgoingManual(Model model) {
        commonService.addCompanyList(model);
        commonService.addPlantList(model);
        return "task-outgoing-manual";
    }

    @RequestMapping("/task/part/log")
    public String taskPartLog(Model model) {
        commonService.addCompanyList(model);
        return "task-part-log";
    }

    @RequestMapping("/task/part/manage")
    public String taskPartManage(Model model) {
        return "task-part-manage";
    }

    @RequestMapping("/task/part/stock")
    public String taskPartStock(Model model) {
        commonService.addCompanyList(model);
        model.addAttribute("partStockList", partStockService.getPartStockListAll());
        model.addAttribute("companyCode", null);
        return "task-part-stock";
    }

    @RequestMapping("/task/part/stock/{companyCode}")
    public String taskPartStockCompanyCode(@PathVariable String companyCode, Model model) {
        commonService.addCompanyList(model);
        model.addAttribute("partStockList", partStockService.getPartStockList(companyCode));
        model.addAttribute("companyCode", companyCode);
        return "task-part-stock";
    }

    @RequestMapping("/task/util/month-cal")
    public String taskUtilMonthCal() {
        return "task-util-month-cal";
    }

//
//    @RequestMapping("/manage/part/bom")
//    public String managePartBom(Model model) {
//        commonService.addCompanyList(model);
//        model.addAttribute("bomList", bomManageService.getBomList());
//        return "manage-part-bom";
//    }

    @RequestMapping("/manage/part/part")
    public String managePartPart(Model model) {
        commonService.addCompanyList(model);
        model.addAttribute("partList", partManageService.getPartList());
        return "manage-part-part";
    }

    @RequestMapping("/manage/part/part/{companyCode}")
    public String managePartPartCompanyCode(@PathVariable String companyCode, Model model) {
        commonService.addCompanyList(model);
        model.addAttribute("partList", partManageService.getPartListAllByCompanyCode(companyCode));
        return "manage-part-part";
    }
}