package com.lyn.jxc.dao;

import com.lyn.jxc.entity.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerDao {
    List<Customer> list(@Param("offset") Integer offset, @Param("rows") Integer rows,@Param("customerName") String customerName);

    Integer getCustomerCount(@Param("customerName")String customerName);

    void updateCustomer( Customer customer);

    void insertCustomer(@Param("customer") Customer customer);

    void delete(String idss);

    List<Customer> getComboboxList(@Param("q") String q);
}
