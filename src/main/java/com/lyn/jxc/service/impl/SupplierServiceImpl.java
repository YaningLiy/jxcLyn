package com.lyn.jxc.service.impl;

import com.lyn.jxc.dao.SupplierDao;
import com.lyn.jxc.entity.Log;
import com.lyn.jxc.entity.Supplier;
import com.lyn.jxc.service.LogService;
import com.lyn.jxc.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Resource
    SupplierDao supplierDao;

    @Resource
    LogService logService;

    @Override
    public Map<String, Object> list(Integer page, Integer rows, String supplierName) {
        Map<String, Object> map = new HashMap<>();
        if (page == 0) {
            page = 1;
        }
        Integer offset = (page - 1) * rows;
        List<Supplier> supplierList = supplierDao.list(offset, rows, supplierName);
        Integer total = supplierDao.selectCount(supplierName);
        logService.save(new Log(Log.SELECT_ACTION, "分页查询供应商"));
        map.put("total", total);
        map.put("rows", supplierList);
        return map;
    }

    @Override
    public void save(Integer supplierId, Supplier supplier) {
        if (supplierId != null) {
            supplierDao.updateSupplier(supplierId, supplier);
            logService.save(new Log(Log.UPDATE_ACTION, "更新供应商：" + supplierId));
        } else {
            supplierDao.insertSupplier(supplier);
            logService.save(new Log(Log.UPDATE_ACTION, "添加供应商：" + supplier));
        }
    }

    @Override
    public void delete(String ids) {
        String[] idss = ids.split(",");
        supplierDao.deleteSupplier(idss);
        logService.save(new Log(Log.DELETE_ACTION, "删除供应商" + ids));
    }

    @Override
    public List<Supplier> getComboboxList(String q) {
        List<Supplier> supplierList = supplierDao.getComboboxList(q);
        return supplierList;
    }
}
