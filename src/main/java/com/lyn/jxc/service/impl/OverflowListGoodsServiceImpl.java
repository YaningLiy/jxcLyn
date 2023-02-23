package com.lyn.jxc.service.impl;

import com.lyn.jxc.dao.GoodsDao;
import com.lyn.jxc.dao.OverflowListDao;
import com.lyn.jxc.dao.OverflowListGoodsDao;
import com.lyn.jxc.dao.UserDao;
import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;
import com.lyn.jxc.entity.*;
import com.lyn.jxc.service.LogService;
import com.lyn.jxc.service.OverflowListGoodsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OverflowListGoodsServiceImpl implements OverflowListGoodsService {
    @Resource
    OverflowListGoodsDao overflowListGoodsDao;
    @Resource
    UserDao userDao;
    @Resource
    LogService logService;
    @Resource
    OverflowListDao overflowListDao;
    @Resource
    GoodsDao goodsDao;
    @Transactional
    @Override
    public ServiceVO save(OverflowList overflowList, String overflowListGoodsStr) {
        List<OverflowListGoods> overflowListGoodsList = new Gson().fromJson(overflowListGoodsStr,new TypeToken<List<OverflowListGoods>>(){}.getType());
        //获取当前用户登录信息
        User currentUser = userDao.findUserByName((String) SecurityUtils.getSubject().getPrincipal());
        overflowList.setUserId(currentUser.getUserId());
        overflowListDao.save(overflowList);
        logService.save(new Log(Log.INSERT_ACTION, "新增商品报溢单"+overflowList.getOverflowListId()));
        overflowListGoodsList.forEach(overflowListGoods -> {
            overflowListGoods.setOverflowListId(overflowList.getOverflowListId());
            overflowListGoodsDao.save(overflowListGoods);
            Goods goods = goodsDao.getGoods(overflowListGoods.getGoodsId());
            goods.setState(2);
            goods.setInventoryQuantity(goods.getInventoryQuantity()+overflowListGoods.getGoodsNum());
            goodsDao.update(goods);
        });
        logService.save(new Log(Log.INSERT_ACTION, "新增商品报溢列表"+overflowList.getOverflowListId()));

        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> list(String sTime, String eTime) {
        List<DamageList> overflowList = overflowListDao.getOverflowList(sTime, eTime);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",overflowList);
        logService.save(new Log(Log.SELECT_ACTION, "商品报溢单据查询"));
        return map;
    }

    @Override
    public Map<String, Object> goodsList(Integer overflowListId) {
        List<OverflowListGoods> OverflowListGoodsList = overflowListGoodsDao.getOverflowListGoods(overflowListId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",OverflowListGoodsList);
        logService.save(new Log(Log.SELECT_ACTION, "商品报溢商品列表查询"));
        return map;
    }
}
