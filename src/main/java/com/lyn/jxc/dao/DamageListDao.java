package com.lyn.jxc.dao;

import com.lyn.jxc.entity.DamageList;

import java.util.List;

public interface DamageListDao {
    Integer save(DamageList damageList);

    List<DamageList> getDamagelist(String sTime, String eTime);
}
