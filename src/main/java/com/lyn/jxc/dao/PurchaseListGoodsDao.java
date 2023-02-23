package com.lyn.jxc.dao;

import com.lyn.jxc.entity.PurchaseListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseListGoodsDao {



    void insert(PurchaseListGoods purchaseListGoods);

    List<PurchaseListGoods> getPurchaseListGoods(Integer purchaseListId);

    void deleteBypurchaseListId(Integer purchaseListId);

    List<PurchaseListGoods> getPurchaseListGoodsByGoodsTypeIdAndCodeOrName(@Param("purchaseListId") Integer purchaseListId, @Param("goodsTypeId") Integer goodsTypeId,@Param("codeOrName") String codeOrName);
}
