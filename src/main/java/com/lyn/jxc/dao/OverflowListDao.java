package com.lyn.jxc.dao;

import com.lyn.jxc.entity.DamageList;
import com.lyn.jxc.entity.OverflowList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OverflowListDao {
    void save(OverflowList overflowList);

    List<DamageList> getOverflowList(@Param("sTime") String sTime,@Param("eTime") String eTime);
}
