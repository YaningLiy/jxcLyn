package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;
import com.lyn.jxc.entity.Goods;
import com.lyn.jxc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    /**
     * @param page
     * @param rows
     * @param codeOrName
     * @param goodsTypeId
     * @return
     * @athor b1
     */
    @ResponseBody
    @RequestMapping("/listInventory")
    public Map<String, Object> listInventory(Integer page, Integer rows, String codeOrName, Integer goodsTypeId) {
        Map<String, Object> map = goodsService.listInventory(page, rows, codeOrName, goodsTypeId);
        return map;
    }

    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(Integer page, Integer rows, String goodsName, Integer goodsTypeId) {
        Map<String, Object> map = goodsService.list(page, rows, goodsName, goodsTypeId);
        return map;
    }

    @PostMapping("save")
    @ResponseBody
    public ServiceVO save(Goods goods) {
        goodsService.save(goods);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @PostMapping("delete")
    @ResponseBody
    public ServiceVO delete(Integer goodsId) {
        ServiceVO serviceVO = goodsService.delete(goodsId);
        return serviceVO;
    }

    @PostMapping("getNoInventoryQuantity")
    @ResponseBody
    public Map<String, Object> getNoInventoryQuantity(Integer page, Integer rows, String nameOrCode) {
        Map<String, Object> map = goodsService.getNoInventoryQuantity(page, rows, nameOrCode);
        return map;
    }

    @PostMapping("getHasInventoryQuantity")
    @ResponseBody
    public Map<String, Object> getHasInventoryQuantity(Integer page, Integer rows, String nameOrCode) {
        Map<String, Object> map = goodsService.getHasInventoryQuantity(page, rows, nameOrCode);
        return map;
    }

    @PostMapping("saveStock")
    @ResponseBody
    public ServiceVO saveStock(Integer goodsId,Integer inventoryQuantity,Double purchasingPrice) {
       ServiceVO serviceVO =  goodsService.saveStock(goodsId,inventoryQuantity,purchasingPrice);
        return serviceVO;
    }
    @PostMapping("deleteStock")
    @ResponseBody
    public ServiceVO deleteStock(Integer goodsId) {
        ServiceVO serviceVO = goodsService.deleteStock(goodsId);
        return serviceVO;
    }
    @PostMapping("listAlarm")
    @ResponseBody
    public Map<String,Object> listAlarm() {
        Map<String,Object> map = goodsService.listAlarm();

        return map;
    }
}
