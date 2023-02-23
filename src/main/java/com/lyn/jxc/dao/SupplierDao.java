package com.lyn.jxc.dao;

import com.lyn.jxc.entity.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierDao {
    List<Supplier> list(@Param("offset") Integer offset,@Param("rows") Integer rows, @Param("supplierName")String supplierName);

    Integer selectCount(@Param("supplierName")String supplierName);

    void updateSupplier(@Param("supplierId") Integer supplierId, @Param("supplier") Supplier supplier);

    void insertSupplier(@Param("supplier") Supplier supplier);

    void deleteSupplier(@Param("idss") String[] idss);

    List<Supplier> getComboboxList(String supplierName);
}
