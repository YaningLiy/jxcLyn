package com.lyn.jxc.dao;

import com.lyn.jxc.entity.PurchaseList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseListDao {
    void insert(PurchaseList purchaseList);

    List<PurchaseList> getpurchaseList(@Param("purchaseNumber") String purchaseNumber,@Param("supplierId") Integer supplierId, @Param("state") Integer state, @Param("sTime") String sTime, @Param("eTime") String eTime);


    void deleteByPurchaseListId(Integer purchaseListId);

    void updateStateByPurchaseListId(Integer purchaseListId);
}
