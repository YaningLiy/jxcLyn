package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.PurchaseList;
import com.lyn.jxc.service.PurchaseListGoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("purchaseListGoods")
public class PurchaseListGoodsController {
    @Resource
    PurchaseListGoodsService purchaseListGoodsService;

    @PostMapping("save")
    public ServiceVO save(PurchaseList purchaseList, String purchaseListGoodsStr) {
        ServiceVO serviceVO = purchaseListGoodsService.save(purchaseList, purchaseListGoodsStr);
        return serviceVO;
    }

    @PostMapping("list")
    public Map<String, Object> list(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime) {
        Map<String, Object> map = purchaseListGoodsService.list(purchaseNumber, supplierId, state, sTime, eTime);
        return map;
    }

    @PostMapping("goodsList")
    public Map<String, Object> goodsList(Integer purchaseListId) {
        Map<String, Object> map = purchaseListGoodsService.goodsList(purchaseListId);
        return map;
    }

    @PostMapping("delete")
    public ServiceVO delete(Integer purchaseListId) {
        ServiceVO serviceVO = purchaseListGoodsService.delete(purchaseListId);
        return serviceVO;
    }

    /**
     * 支付结算（修改进货单付款状态）
     *
     * @param purchaseListId
     * @return
     */
    @PostMapping("updateState")
    public ServiceVO updateState(Integer purchaseListId) {
        ServiceVO serviceVO = purchaseListGoodsService.updateState(purchaseListId);
        return serviceVO;
    }

    /**
     * 进货统计（可根据 商品类别、商品编码或名称 条件查询）
     * @param sTime
     * @param eTime
     * @param goodsTypeId
     * @param codeOrName
     * @return
     */
    @PostMapping("count")
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        String jsonString = purchaseListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
        return jsonString;
    }
}
