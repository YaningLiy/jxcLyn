package com.lyn.jxc.service;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.CustomerReturnList;

import java.util.Map;

public interface CustomerReturnListGoodsService {
    Integer getCustomerReturnTotalByGoodsId(Integer goodsId);

    ServiceVO save(CustomerReturnList customerReturnList, String customerReturnListGoodsStr);

    Map<String, Object> goodsList(Integer customerReturnListId);

    Map<String, Object> list(String returnNumber, Integer customerId, Integer state, String sTime, String eTime);

    ServiceVO delete(Integer customerReturnListId);

    String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
