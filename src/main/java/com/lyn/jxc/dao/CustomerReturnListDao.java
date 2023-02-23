package com.lyn.jxc.dao;

import com.lyn.jxc.entity.CustomerReturnList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerReturnListDao {
    void insert(CustomerReturnList customerReturnList);

    List<CustomerReturnList> getCustomerReturnList(@Param("returnNumber") String returnNumber, @Param("customerId") Integer customerId,@Param("state") Integer state, @Param("sTime") String sTime,@Param("eTime") String eTime);

    void deleteByCustomerReturnListId(Integer customerReturnListId);
}
