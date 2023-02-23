package com.lyn.jxc.service;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.Goods;

import java.util.Map;

public interface GoodsService {
    Map<String, Object> listInventory(Integer page, Integer rows, String codeOrName, Integer goodsTypeId);

    Map<String, Object> list(Integer page, Integer rows, String codeOrName, Integer goodsTypeId);

    void save(Goods goods);

    ServiceVO delete(Integer goodsId);

    Map<String, Object> getNoInventoryQuantity(Integer page, Integer rows, String nameOrCode);

    Map<String, Object> getHasInventoryQuantity(Integer page, Integer rows, String nameOrCode);

    ServiceVO saveStock(Integer goodsId,Integer inventoryQuantity,Double purchasingPrice);

    ServiceVO deleteStock(Integer goodsId);

    Map<String, Object> listAlarm();
}
