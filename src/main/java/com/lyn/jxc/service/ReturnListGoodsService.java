package com.lyn.jxc.service;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.ReturnList;

import java.util.Map;

public interface ReturnListGoodsService {
    ServiceVO save(ReturnList returnList, String returnListGoodsStr);

    Map<String, Object> list(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime);

    Map<String, Object> goodsList(Integer returnListId);

    ServiceVO delete(Integer returnListId);

    String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
