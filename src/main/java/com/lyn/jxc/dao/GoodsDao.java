package com.lyn.jxc.dao;

import com.lyn.jxc.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GoodsDao {

    List<Goods> listInventory(@Param("offset") Integer offset, @Param("rows") Integer rows, @Param("codeOrName") String codeOrName, @Param("goodsTypeId") Integer goodsTypeId);

    Integer getGoodsCount(@Param("codeOrName") String codeOrName, @Param("goodsTypeId") Integer goodsTypeId);

    Integer getGoodsInventoryCount(@Param("codeOrName") String codeOrName, @Param("goodsTypeId") Integer goodsTypeId);

    List<Goods> list(@Param("offset") Integer offset, @Param("rows") Integer rows, @Param("goodsName") String goodsName, @Param("goodsTypeId") Integer goodsTypeId);

    void insert(Goods goods);

    void update(Goods goods);

    Goods getGoods(Integer goodsId);

    void delete(Integer goodsId);

    List<Goods> getNoInventoryQuantity(@Param("offset") Integer offset,@Param("rows") Integer rows,@Param("nameOrCode") String nameOrCode);

    Integer getNoInventoryQuantityCount(String nameOrCode);

    List<Goods> getHasInventoryQuantity(@Param("offset") Integer offset,@Param("rows") Integer rows,@Param("nameOrCode") String nameOrCode);

    Integer getHasInventoryQuantityCount(String nameOrCode);


    List<Goods> ltMinNumGoodsList();

    List<Goods> ltMinNumGoodsListTest(@Param("page") String page);
}
