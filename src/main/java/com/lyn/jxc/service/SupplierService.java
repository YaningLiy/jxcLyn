package com.lyn.jxc.service;

import com.lyn.jxc.entity.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierService {

    Map<String, Object> list(Integer page, Integer rows, String supplierName);

    void save(Integer supplierId,Supplier supplier);

    void delete(String ids);

    List<Supplier> getComboboxList(String q);
}
