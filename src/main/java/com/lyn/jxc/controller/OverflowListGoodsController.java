package com.lyn.jxc.controller;

import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.entity.OverflowList;
import com.lyn.jxc.service.OverflowListGoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/overflowListGoods")
public class OverflowListGoodsController {
    @Resource
    OverflowListGoodsService overflowListGoodsService;

    @PostMapping("save")
    public ServiceVO save(OverflowList overflowList, String overflowListGoodsStr) {
        ServiceVO serviceVO = overflowListGoodsService.save(overflowList, overflowListGoodsStr);
        return serviceVO;
    }
    @PostMapping("list")
    @ResponseBody
    public Map<String, Object> list(String sTime, String eTime) {
        Map<String, Object> map = overflowListGoodsService.list(sTime, eTime);
        return map;
    }
    @PostMapping("goodsList")
    @ResponseBody
    public Map<String, Object> goodsList(Integer overflowListId) {
        Map<String, Object> map = overflowListGoodsService.goodsList(overflowListId);
        return map;
    }
}
