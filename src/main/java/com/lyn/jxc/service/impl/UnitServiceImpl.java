package com.lyn.jxc.service.impl;


import com.lyn.jxc.dao.UnitDao;
import com.lyn.jxc.entity.Log;
import com.lyn.jxc.entity.Unit;
import com.lyn.jxc.service.LogService;
import com.lyn.jxc.service.UnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnitServiceImpl implements UnitService {
    @Resource
    LogService logService;
    @Resource
    UnitDao unitDao;
    @Override
    public Map<String, Object> list() {
        List<Unit> unitList = unitDao.getList();
        HashMap<String, Object> map = new HashMap<>();
        logService.save(new Log(Log.SELECT_ACTION,"查询所有商品单位"));
        map.put("rows",unitList);
        return map;
    }
}
