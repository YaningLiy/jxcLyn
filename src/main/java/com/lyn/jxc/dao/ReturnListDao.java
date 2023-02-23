package com.lyn.jxc.dao;

import com.lyn.jxc.entity.ReturnList;

import java.util.List;

public interface ReturnListDao {
    void insert(ReturnList returnList);

    List<ReturnList> getReturnListDao(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime);

    void deleteByReturnListId(Integer returnListId);
}
