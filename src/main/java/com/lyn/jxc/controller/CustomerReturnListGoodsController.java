package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.CustomerReturnList;
import com.lyn.jxc.service.CustomerReturnListGoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("customerReturnListGoods")
public class CustomerReturnListGoodsController {
    @Resource
    CustomerReturnListGoodsService customerReturnListGoodsService;

    @PostMapping("save")
    public ServiceVO save(CustomerReturnList customerReturnList, String customerReturnListGoodsStr) {
        ServiceVO serviceVO = customerReturnListGoodsService.save(customerReturnList, customerReturnListGoodsStr);
        return serviceVO;
    }
    @PostMapping("list")
    public Map<String,Object> list(String returnNumber, Integer customerId, Integer state, String sTime,String eTime) {
        Map<String,Object> map = customerReturnListGoodsService.list(returnNumber, customerId,  state,  sTime, eTime);
        return map;
    }
    @PostMapping("goodsList")
    public Map<String,Object> goodsList(Integer customerReturnListId) {
        Map<String,Object> map = customerReturnListGoodsService.goodsList(customerReturnListId);
        return map;
    }
    @PostMapping("delete")
    public ServiceVO delete(Integer customerReturnListId) {
        ServiceVO serviceVO = customerReturnListGoodsService.delete(customerReturnListId);
        return serviceVO;
    }

    @PostMapping("count")
    public String count(String sTime, String eTime ,Integer goodsTypeId, String codeOrName) {
        String jsonString = customerReturnListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
        return jsonString;
    }
}
