package com.lyn.jxc.service.impl;

import com.lyn.jxc.dao.*;
import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;
import com.lyn.jxc.entity.*;
import com.lyn.jxc.service.LogService;
import com.lyn.jxc.service.ReturnListGoodsService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService {
    @Resource
    LogService logService;
    @Resource
    UserDao userDao;
    @Resource
    GoodsDao goodsDao;
    @Resource
    GoodsTypeDao goodsTypeDao;
    @Resource
    ReturnListGoodsDao returnListGoodsDao;
    @Resource
    ReturnListDao returnListDao;

    @Override
    public ServiceVO save(ReturnList returnList, String returnListGoodsStr) {
        List<ReturnListGoods> returnListGoodsList = new Gson().fromJson(returnListGoodsStr, new TypeToken<List<ReturnListGoods>>() {
        }.getType());
        User user = userDao.findUserByName((String) SecurityUtils.getSubject().getPrincipal());
        returnList.setUserId(user.getUserId());
        returnListDao.insert(returnList);
        returnListGoodsList.forEach(returnListGoods -> {
            returnListGoods.setReturnListId(returnList.getReturnListId());
            returnListGoodsDao.insert(returnListGoods);
            Goods goods = goodsDao.getGoods(returnListGoods.getGoodsId());
            goods.setState(2);
            goods.setInventoryQuantity(goods.getInventoryQuantity() - returnListGoods.getGoodsNum());
            goodsDao.update(goods);
        });
        logService.save(new Log(Log.INSERT_ACTION, "新增退货单:" + returnList.getReturnListId()));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> list(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime) {
        List<ReturnList> returnList = returnListDao.getReturnListDao(returnNumber, supplierId, state, sTime, eTime);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", returnList);
        logService.save(new Log(Log.SELECT_ACTION, "退货单列表展示"));
        return map;
    }

    @Override
    public Map<String, Object> goodsList(Integer returnListId) {
        List<ReturnListGoods> returnListGoodsList = returnListGoodsDao.getReturnListGoods(returnListId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", returnListGoodsList);
        logService.save(new Log(Log.SELECT_ACTION, "退货单商品信息"));
        return map;
    }

    @Transactional
    @Override
    public ServiceVO delete(Integer returnListId) {
        returnListGoodsDao.deleteByReturnListId(returnListId);
        returnListDao.deleteByReturnListId(returnListId);
        logService.save(new Log(Log.DELETE_ACTION, "进货单删除"));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        JsonArray jsonArray = new JsonArray();
        List<ReturnList> returnListList = returnListDao.getReturnListDao(null, null, null, sTime, eTime);
        returnListList.forEach(returnList -> {
            List<ReturnListGoods> returnListGoodsList = returnListGoodsDao.getReturnListGoodsListByGoodsTypeIdAndCodeOrName(returnList.getReturnListId(), goodsTypeId, codeOrName);
            returnListGoodsList.forEach(returnListGoods -> {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("number", returnList.getReturnNumber());
                jsonObject.addProperty("date", returnList.getReturnDate());
                jsonObject.addProperty("supplierName", returnList.getSupplierName());
                jsonObject.addProperty("code", returnListGoods.getGoodsCode());
                jsonObject.addProperty("name", returnListGoods.getGoodsName());
                jsonObject.addProperty("model", returnListGoods.getGoodsModel());
                jsonObject.addProperty("goodsType", goodsTypeDao.getGoodsTypeById(returnListGoods.getGoodsTypeId()).getGoodsTypeName());
                jsonObject.addProperty("unit", returnListGoods.getGoodsUnit());
                jsonObject.addProperty("price", returnListGoods.getPrice());
                jsonObject.addProperty("num", returnListGoods.getGoodsNum());
                jsonObject.addProperty("total", returnListGoods.getTotal());

                jsonArray.add(jsonObject);

            });
        });
        logService.save(new Log(Log.SELECT_ACTION, "退货统计"));
        return jsonArray.toString();
    }
}
