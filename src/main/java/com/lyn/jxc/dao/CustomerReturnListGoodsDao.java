package com.lyn.jxc.dao;

import com.lyn.jxc.entity.CustomerReturnListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerReturnListGoodsDao {
    Integer getCustomerReturnTotalByGoodsId(Integer goodsId);

    void insert(CustomerReturnListGoods customerReturnListGoodsList);

    List<CustomerReturnListGoods> getCustomerReturnListGoods(Integer customerReturnListId);

    void deleteByCustomerReturnListId(Integer customerReturnListId);

    List<CustomerReturnListGoods> getCustomerReturnListGoodsListByGoodsTypeIdAndCodeOrName(@Param("customerReturnListId") Integer customerReturnListId,@Param("goodsTypeId") Integer goodsTypeId,@Param("codeOrName") String codeOrName);
}
