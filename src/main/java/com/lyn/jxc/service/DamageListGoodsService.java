package com.lyn.jxc.service;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.DamageList;

import java.util.Map;

public interface DamageListGoodsService {
    ServiceVO save(DamageList damageList, String damageListGoodsStr);

    Map<String, Object> list(String sTime, String eTime);

    Map<String, Object> goodsList(Integer damageListId);
}
