package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;
import com.lyn.jxc.entity.Customer;
import com.lyn.jxc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(Integer page, Integer rows, String customerName) {
        Map<String, Object> map = customerService.list(page, rows, customerName);
        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ServiceVO save(Customer customer) {
        customerService.save(customer);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ServiceVO delete(String ids) {
        customerService.delete(ids);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @RequestMapping("/getComboboxList")
    @ResponseBody
    public List<Customer> getComboboxList(String q) {
        return customerService.getComboboxList(q);
    }
}
