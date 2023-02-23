package com.lyn.jxc.service;

import com.lyn.jxc.entity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    Map<String, Object> list(Integer page, Integer rows, String customerName);

    void save(Customer customer);

    void delete(String ids);

    List<Customer> getComboboxList(String q);
}
