package com.lyn.jxc.service;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.OverflowList;

import java.util.Map;

public interface OverflowListGoodsService {
    ServiceVO save(OverflowList overflowList, String overflowListGoodsStr);

    Map<String, Object> list(String sTime, String eTime);

    Map<String, Object> goodsList(Integer damageListId);
}
