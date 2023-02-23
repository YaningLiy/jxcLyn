package com.lyn.jxc.service.impl;

import com.lyn.jxc.dao.CustomerDao;
import com.lyn.jxc.entity.Customer;
import com.lyn.jxc.entity.Log;
import com.lyn.jxc.service.CustomerService;
import com.lyn.jxc.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    CustomerDao customerDao;
    @Resource
    LogService logService;

    @Override
    public Map<String, Object> list(Integer page, Integer rows, String customerName) {
        if (page == 0) {
            page = 1;
        }
        Integer offset = (page - 1) * rows;
        List<Customer> customerList = customerDao.list(offset, rows, customerName);
        Integer total = customerDao.getCustomerCount(customerName);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", customerList);

        return map;
    }

    @Override
    public void save(Customer customer) {
        if (customer.getCustomerId() != null) {
            customerDao.updateCustomer(customer);
            logService.save(new Log(Log.UPDATE_ACTION, "更新客户信息：" + customer.getCustomerName()));
        } else {
            customerDao.insertCustomer(customer);
            logService.save(new Log(Log.INSERT_ACTION, "新增用户信息：" + customer.getCustomerName()
            ));
        }
    }

    @Override
    public void delete(String ids) {
        String[] idss = ids.split(",");
        for (int i = 0; i < idss.length; i++) {
            customerDao.delete(idss[i]);
        }
    }

    @Override
    public List<Customer> getComboboxList(String q) {
        return customerDao.getComboboxList(q);

    }

}
