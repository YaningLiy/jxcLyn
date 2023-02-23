package com.lyn.jxc.dao;

import com.lyn.jxc.entity.SaleList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleListDao {
    void insert(SaleList saleList);

    List<SaleList> getSaleList(@Param("saleNumber") String saleNumber,
                               @Param("customerId") Integer customerId,
                               @Param("state") Integer state,
                               @Param("sTime") String sTime,
                               @Param("eTime") String eTime);

    void deleteBySaleListId(Integer saleListId);

    void updateStateBySaleListId(Integer saleListId);
}
