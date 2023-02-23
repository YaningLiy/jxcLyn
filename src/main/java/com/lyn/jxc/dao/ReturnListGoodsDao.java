package com.lyn.jxc.dao;

import com.lyn.jxc.entity.ReturnListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnListGoodsDao {
    void insert(ReturnListGoods returnListGoods);

    List<ReturnListGoods> getReturnListGoods(Integer returnListId);

    void deleteByReturnListId(Integer returnListId);

    List<ReturnListGoods> getReturnListGoodsListByGoodsTypeIdAndCodeOrName(@Param("returnListId") Integer returnListId, @Param("goodsTypeId") Integer goodsTypeId, @Param("codeOrName") String codeOrName);
}
