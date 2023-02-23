package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.ReturnList;
import com.lyn.jxc.service.ReturnListGoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("returnListGoods")
public class ReturnListGoodsController {
    @Resource
    ReturnListGoodsService returnListGoodsService;

    @PostMapping("save")
    public ServiceVO save(ReturnList returnList, String returnListGoodsStr) {
        ServiceVO serviceVO = returnListGoodsService.save(returnList, returnListGoodsStr);
        return serviceVO;
    }
    @PostMapping("list")
    public Map<String,Object> list(String returnNumber,Integer supplierId, Integer state,String sTime,String eTime) {
        Map<String,Object> map = returnListGoodsService.list(returnNumber, supplierId,  state,  sTime, eTime);
        return map;
    }
    @PostMapping("goodsList")
    public Map<String,Object> goodsList( Integer returnListId) {
        Map<String,Object> map = returnListGoodsService.goodsList(returnListId);
        return map;
    }
    @PostMapping("delete")
    public ServiceVO delete(Integer returnListId) {
        ServiceVO serviceVO = returnListGoodsService.delete(returnListId);
        return serviceVO;
    }
    @PostMapping("count")
    public String count(String sTime, String eTime ,Integer goodsTypeId, String codeOrName) {
        String jsonString = returnListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
        return jsonString;
    }
}
