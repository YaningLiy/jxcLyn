package com.lyn.jxc.controller;

import com.lyn.jxc.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/unit")
public class UnitController {
    @Resource
    UnitService unitService;
    @PostMapping("/list")
    @ResponseBody
    public Map<String ,Object> list(){
        return unitService.list();
    }
}
