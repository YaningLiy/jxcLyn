package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;
import com.lyn.jxc.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/goodsType")
public class GoodsTypeController {

    @Autowired
    GoodsTypeService goodsTypeService;

    @ResponseBody
    @RequestMapping("/loadGoodsType")
    public String loadGoodsType() {
        String goodsTypeVoListJson = goodsTypeService.loadGoodsType();
        return goodsTypeVoListJson;
    }

    @PostMapping("save")
    @ResponseBody
    public ServiceVO save(String goodsTypeName, Integer pId) {
        goodsTypeService.save(goodsTypeName, pId);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }
    @PostMapping("delete")
    @ResponseBody
    public ServiceVO delete(Integer  goodsTypeId) {
        goodsTypeService.delete( goodsTypeId);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }
}
