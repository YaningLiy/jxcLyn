package com.lyn.jxc.service;

public interface GoodsTypeService {
    String loadGoodsType();

    void save(String goodsTypeName, Integer pId);

    void delete(Integer goodsTypeId);
}
