package com.lyn.jxc.dao;

import com.lyn.jxc.entity.DamageListGoods;

import java.util.List;

public interface DamageListGoodsDao {
    void save(DamageListGoods damageListGoods);



    List<DamageListGoods> getDamageListByDamageListId(Integer damageListId);
}
