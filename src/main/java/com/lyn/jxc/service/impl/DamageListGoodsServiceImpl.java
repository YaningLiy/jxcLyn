package com.lyn.jxc.service.impl;

import com.lyn.jxc.dao.DamageListDao;
import com.lyn.jxc.dao.DamageListGoodsDao;
import com.lyn.jxc.dao.GoodsDao;
import com.lyn.jxc.dao.UserDao;
import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;
import com.lyn.jxc.entity.*;
import com.lyn.jxc.service.DamageListGoodsService;
import com.lyn.jxc.service.LogService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DamageListGoodsServiceImpl implements DamageListGoodsService {
    @Resource
    DamageListDao damageListDao;
    @Resource
    DamageListGoodsDao damageListGoodsDao;
    @Resource
    LogService logService;
    @Resource
    UserDao userDao;
    @Resource
    GoodsDao goodsDao;
    @Override
    public ServiceVO save(DamageList damageList, String damageListGoodsStr) {
        List<DamageListGoods> damageListGoodsList = new Gson().fromJson(damageListGoodsStr,new TypeToken<List<DamageListGoods>>(){}.getType());
        //获取当前用户登录信息
        User currentUser = userDao.findUserByName((String) SecurityUtils.getSubject().getPrincipal());
        damageList.setUserId(currentUser.getUserId());
        Integer influence= damageListDao.save(damageList);
        logService.save(new Log(Log.INSERT_ACTION, "新增商品报损单"+damageList.getDamageListId()));
        damageListGoodsList.forEach(damageListGoods -> {
            damageListGoods.setDamageListId(damageList.getDamageListId());
            Goods goods = goodsDao.getGoods(damageListGoods.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity()-damageListGoods.getGoodsNum());
            goods.setState(2);
            goodsDao.update(goods);
            damageListGoodsDao.save(damageListGoods);
        });
        logService.save(new Log(Log.INSERT_ACTION, "新增商品报损单列表"+damageList.getDamageListId()));

        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> list(String sTime, String eTime) {
        List<DamageList> damageList = damageListDao.getDamagelist(sTime, eTime);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",damageList);
        logService.save(new Log(Log.SELECT_ACTION, "商品报损单据查询"));
        return map;
    }

    @Override
    public Map<String, Object> goodsList(Integer damageListId) {
        List<DamageListGoods> damageListGoodsList = damageListGoodsDao.getDamageListByDamageListId(damageListId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",damageListGoodsList);
        logService.save(new Log(Log.SELECT_ACTION, "商品报损单商品信息查询"));
        return map;
    }
}
