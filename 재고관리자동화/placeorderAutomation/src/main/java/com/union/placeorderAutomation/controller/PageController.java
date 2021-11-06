package com.union.placeorderAutomation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String mainPage() {
        return "task-outgoing";
    }

    @RequestMapping("/task/outgoing")
    public String taskOutgoing() {
        return "task-outgoing";
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
    @RequestMapping("/manage/part/part")
    public String managePartPart() {
        return "manage-part-part";
    }
}