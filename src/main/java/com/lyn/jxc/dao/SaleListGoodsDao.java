package com.lyn.jxc.dao;

import com.lyn.jxc.entity.SaleData;
import com.lyn.jxc.entity.SaleListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleListGoodsDao {
    Integer getSaleTotalByGoodsId(Integer goodsId);

    void insert(SaleListGoods saleListGoods);

    List<SaleListGoods> getSaleListGoodsList(Integer saleListId);

    void deleteBySaleListId(Integer saleListId);

    List<SaleListGoods> getSaleListGoodsListByGoodsTypeIdAndCodeOrName(@Param("saleListId") Integer saleListId, @Param("goodsTypeId") Integer goodsTypeId, @Param("codeOrName") String codeOrName);

    List<SaleData> getSaleDataByDay(@Param("sTime") String sTime, @Param("eTime") String eTime);

    List<SaleData> getSaleDataByMonth(@Param("sTime") String sTime, @Param("eTime") String eTime);
}
