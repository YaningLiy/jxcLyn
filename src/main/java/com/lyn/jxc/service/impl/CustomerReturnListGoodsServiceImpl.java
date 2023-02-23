package com.lyn.jxc.service.impl;

import com.lyn.jxc.dao.*;
import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;
import com.lyn.jxc.entity.*;
import com.lyn.jxc.service.CustomerReturnListGoodsService;
import com.lyn.jxc.service.LogService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService {
    @Resource
    LogService logService;
    @Resource
    CustomerReturnListGoodsDao customerReturnListGoodsDao;
    @Resource
    CustomerReturnListDao customerReturnListDao;
    @Resource
    UserDao userDao;

    @Resource
    GoodsDao goodsDao;
    @Resource
    GoodsTypeDao goodsTypeDao;

    @Override
    public Integer getCustomerReturnTotalByGoodsId(Integer goodsId) {
        logService.save(new Log(Log.SELECT_ACTION, "查询顾客退款商品数量" + goodsId));
        Integer customerReturnTotalByGoodsId = customerReturnListGoodsDao.getCustomerReturnTotalByGoodsId(goodsId);
        if (customerReturnTotalByGoodsId == null) {
            customerReturnTotalByGoodsId = 0;
        }
        return customerReturnTotalByGoodsId;
    }

    @Override
    public ServiceVO save(CustomerReturnList customerReturnList, String customerReturnListGoodsStr) {
        List<CustomerReturnListGoods> customerReturnListGoodsList = new Gson().fromJson(customerReturnListGoodsStr, new TypeToken<List<CustomerReturnListGoods>>() {
        }.getType());
        User user = userDao.findUserByName((String) SecurityUtils.getSubject().getPrincipal());
        customerReturnList.setUserId(user.getUserId());
        customerReturnListDao.insert(customerReturnList);
        customerReturnListGoodsList.forEach(customerReturnListGoods -> {
            customerReturnListGoods.setCustomerReturnListId(customerReturnList.getCustomerReturnListId());
            customerReturnListGoodsDao.insert(customerReturnListGoods);
            Goods goods = goodsDao.getGoods(customerReturnListGoods.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity() + customerReturnListGoods.getGoodsNum());
            goods.setState(2);
            goodsDao.update(goods);
        });
        logService.save(new Log(Log.INSERT_ACTION, "退货单保存:" + customerReturnList.getReturnNumber()));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> list(String returnNumber, Integer customerId, Integer state, String sTime, String eTime) {
        List<CustomerReturnList> customerReturnListList = customerReturnListDao.getCustomerReturnList(returnNumber, customerId, state, sTime, eTime);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", customerReturnListList);
        logService.save(new Log(Log.SELECT_ACTION, "进货单列表查询"));
        return map;
    }

    @Override
    public Map<String, Object> goodsList(Integer customerReturnListId) {
        List<CustomerReturnListGoods> customerReturnListGoodsList = customerReturnListGoodsDao.getCustomerReturnListGoods(customerReturnListId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", customerReturnListGoodsList);
        logService.save(new Log(Log.SELECT_ACTION, "进货单商品信息"));
        return map;
    }

    @Override
    public ServiceVO delete(Integer customerReturnListId) {
        customerReturnListGoodsDao.deleteByCustomerReturnListId(customerReturnListId);
        customerReturnListDao.deleteByCustomerReturnListId(customerReturnListId);
        logService.save(new Log(Log.DELETE_ACTION, "进货单删除"));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        JsonArray jsonArray = new JsonArray();
        List<CustomerReturnList> customerReturnListList = customerReturnListDao.getCustomerReturnList(null, null, null, sTime, eTime);
        customerReturnListList.forEach(customerReturnList -> {
            List<CustomerReturnListGoods> customerReturnListGoodsList = customerReturnListGoodsDao.getCustomerReturnListGoodsListByGoodsTypeIdAndCodeOrName(customerReturnList.getCustomerReturnListId(), goodsTypeId, codeOrName);
            customerReturnListGoodsList.forEach(customerReturnListGoods -> {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("number", customerReturnList.getReturnNumber());
                jsonObject.addProperty("date", customerReturnList.getReturnDate());
                jsonObject.addProperty("customerName", customerReturnList.getCustomerName());
                jsonObject.addProperty("code", customerReturnListGoods.getGoodsCode());
                jsonObject.addProperty("name", customerReturnListGoods.getGoodsName());
                jsonObject.addProperty("model", customerReturnListGoods.getGoodsModel());
                jsonObject.addProperty("goodsType", goodsTypeDao.getGoodsTypeById(customerReturnListGoods.getGoodsTypeId()).getGoodsTypeName());
                jsonObject.addProperty("unit", customerReturnListGoods.getGoodsUnit());
                jsonObject.addProperty("price", customerReturnListGoods.getPrice());
                jsonObject.addProperty("num", customerReturnListGoods.getGoodsNum());
                jsonObject.addProperty("total", customerReturnListGoods.getTotal());

                jsonArray.add(jsonObject);

            });
        });
        logService.save(new Log(Log.SELECT_ACTION, "客户退货统计"));
        return jsonArray.toString();
    }
}
