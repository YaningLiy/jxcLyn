package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.SaleList;
import com.lyn.jxc.service.SaleListGoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("saleListGoods")
public class SaleListGoodsController {

    @Resource
    SaleListGoodsService saleListGoodsService;

    @PostMapping("save")
    public ServiceVO save(SaleList saleList, String saleListGoodsStr) {
        ServiceVO serviceVO = saleListGoodsService.save(saleList, saleListGoodsStr);
        return serviceVO;
    }
    @PostMapping("list")
    public Map<String,Object> list(String saleNumber, Integer customerId, Integer state, String sTime,String eTime) {
        Map<String,Object> map = saleListGoodsService.list(saleNumber, customerId,  state,  sTime, eTime);
        return map;
    }
    @PostMapping("goodsList")
    public Map<String,Object> goodsList(Integer saleListId) {
        Map<String,Object> map = saleListGoodsService.goodsList(saleListId);
        return map;
    }
    @PostMapping("delete")
    public ServiceVO delete(Integer saleListId) {
        ServiceVO serviceVO = saleListGoodsService.delete(saleListId);
        return serviceVO;
    }
    /**
     * 支付结算（修改销售单付款状态）
     * @param saleListId
     * @return
     */
    @PostMapping("updateState")
    public ServiceVO updateState(Integer saleListId) {
        ServiceVO serviceVO = saleListGoodsService.updateState(saleListId);
        return serviceVO;
    }
    @PostMapping("count")
    public String count(String sTime, String eTime ,Integer goodsTypeId, String codeOrName) {
        String jsonString = saleListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
        return jsonString;
    }

    @PostMapping("getSaleDataByDay")
    public String getSaleDataByDay(String sTime, String eTime) {
        String jsonString = saleListGoodsService.getSaleDataByDay(sTime, eTime);
        return jsonString;
    }

    @PostMapping("getSaleDataByMonth")
    public String getSaleDataByMonth(String sTime, String eTime) {
        String jsonString = saleListGoodsService.getSaleDataByMonth(sTime, eTime);
        return jsonString;
    }

}
