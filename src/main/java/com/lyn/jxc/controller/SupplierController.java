package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.Supplier;
import com.lyn.jxc.service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Resource
    SupplierService supplierService;

    /**
     * @param page
     * @param rows
     * @param supplierName
     * @return
     * @author b3
     */
    //Integer page（当前页数）, Integer rows（每页显示的记录数）, String supplierName
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(Integer page, Integer rows, String supplierName) {
        Map<String,Object> map = supplierService.list(page, rows, supplierName);
        return map;
    }

    /**
     *
     * @param supplierId
     * @param supplier
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public ServiceVO save(@RequestParam(value = "supplierId",required = false) Integer supplierId, Supplier supplier){
        supplierService.save(supplierId,supplier);
        return new ServiceVO(200,"请求成功",null);
    }

    /**
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public ServiceVO delete(@RequestParam("ids") String  ids){
        supplierService.delete(ids);
        return new ServiceVO(100,"请求成功",null);
    }

    @PostMapping("/getComboboxList")
    @ResponseBody
    public List<Supplier> getComboboxList(String q){

        return supplierService.getComboboxList(q);
    }
}
