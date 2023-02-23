package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.DamageList;
import com.lyn.jxc.service.DamageListGoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/damageListGoods")
public class DamageListGoodsController {
    @Resource
    DamageListGoodsService damageListGoodsService;

    @PostMapping("save")
    @ResponseBody
    public ServiceVO save(DamageList damageList, String damageListGoodsStr) {
        ServiceVO serviceVO = damageListGoodsService.save(damageList, damageListGoodsStr);
        return serviceVO;
    }

    @PostMapping("list")
    @ResponseBody
    public Map<String, Object> list(String sTime, String eTime) {
        Map<String, Object> map = damageListGoodsService.list(sTime, eTime);
        return map;
    }
    @PostMapping("goodsList")
    @ResponseBody
    public Map<String, Object> goodsList(Integer damageListId) {
        Map<String, Object> map = damageListGoodsService.goodsList(damageListId);
        return map;
    }
}
