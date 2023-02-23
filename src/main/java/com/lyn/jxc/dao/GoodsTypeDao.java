package com.lyn.jxc.dao;

import com.lyn.jxc.entity.GoodsType;

import java.util.List;

public interface GoodsTypeDao {
    List<GoodsType> loadGoodsType();

    List<GoodsType> getAllGoodsTypeByParentId(Integer parentId);

    void save(GoodsType goodsType);

    void delete(Integer goodsTypeId);

    GoodsType getGoodsTypeById(Integer goodsTypeId);
}
