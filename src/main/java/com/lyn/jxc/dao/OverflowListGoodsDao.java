package com.lyn.jxc.dao;

import com.lyn.jxc.entity.OverflowListGoods;

import java.util.List;

public interface OverflowListGoodsDao {
    void save(OverflowListGoods overflowListGoods);

    List<OverflowListGoods> getOverflowListGoods(Integer damageListId);
}
