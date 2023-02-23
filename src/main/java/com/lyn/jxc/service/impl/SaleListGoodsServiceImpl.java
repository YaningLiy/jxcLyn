package com.lyn.jxc.service.impl;


import com.lyn.jxc.dao.*;
import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;

import com.lyn.jxc.entity.*;
import com.lyn.jxc.service.LogService;
import com.lyn.jxc.service.SaleListGoodsService;
import com.lyn.jxc.util.BigDecimalUtil;
import com.lyn.jxc.util.DateUtil;
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
public class SaleListGoodsServiceImpl implements SaleListGoodsService {
    @Resource
    SaleListGoodsDao saleListGoodsDao;
    @Resource
    SaleListDao saleListDao;
    @Resource
    LogService logService;
    @Resource
    UserDao userDao;
    @Resource
    GoodsDao goodsDao;

    @Resource
    GoodsTypeDao goodsTypeDao;

    @Override
    public Integer getSaleTotalByGoodsId(Integer goodsId) {
        logService.save(new Log(Log.SELECT_ACTION, "查询商品销售数量" + goodsId));
        Integer saleTotalByGoodsId = saleListGoodsDao.getSaleTotalByGoodsId(goodsId);

        return saleTotalByGoodsId == null ? 0 : saleTotalByGoodsId;
    }

    @Override
    public ServiceVO save(SaleList saleList, String saleListGoodsStr) {
        List<SaleListGoods> saleListGoodsList = new Gson().fromJson(saleListGoodsStr, new TypeToken<List<SaleListGoods>>() {
        }.getType());
        User user = userDao.findUserByName((String) SecurityUtils.getSubject().getPrincipal());
        saleList.setUserId(user.getUserId());
        saleListDao.insert(saleList);
        saleListGoodsList.forEach(saleListGoods -> {
            saleListGoods.setSaleListId(saleList.getSaleListId());
            saleListGoodsDao.insert(saleListGoods);
            Goods goods = goodsDao.getGoods(saleListGoods.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity() - saleListGoods.getGoodsNum());
            goods.setState(2);
            goodsDao.update(goods);
        });
        logService.save(new Log(Log.INSERT_ACTION, "销售单保存:" + saleList.getSaleNumber()));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Transactional
    @Override
    public Map<String, Object> list(String saleNumber, Integer customerId, Integer state, String sTime, String eTime) {
        List<SaleList> saleListList = saleListDao.getSaleList(saleNumber, customerId, state, sTime, eTime);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", saleListList);
        logService.save(new Log(Log.SELECT_ACTION, "销售单查询"));
        return map;
    }

    @Transactional
    @Override
    public Map<String, Object> goodsList(Integer saleListId) {
        List<SaleListGoods> saleListGoodsList = saleListGoodsDao.getSaleListGoodsList(saleListId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", saleListGoodsList);
        logService.save(new Log(Log.SELECT_ACTION, "销售单商品信息"));
        return map;
    }

    @Transactional
    @Override
    public ServiceVO delete(Integer saleListId) {
        saleListGoodsDao.deleteBySaleListId(saleListId);
        saleListDao.deleteBySaleListId(saleListId);
        logService.save(new Log(Log.DELETE_ACTION, "删除销售单"));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public ServiceVO updateState(Integer saleListId) {
        saleListDao.updateStateBySaleListId(saleListId);
        logService.save(new Log(Log.UPDATE_ACTION, "支付结算（修改进货单付款状态）:" + saleListId));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        JsonArray jsonArray = new JsonArray();
        List<SaleList> saleListList = saleListDao.getSaleList(null, null, null, sTime, eTime);
        saleListList.forEach(saleList -> {
            List<SaleListGoods> saleListGoodsList = saleListGoodsDao.getSaleListGoodsListByGoodsTypeIdAndCodeOrName(saleList.getSaleListId(), goodsTypeId, codeOrName);
            saleListGoodsList.forEach(saleListGoods -> {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("number", saleList.getSaleNumber());
                jsonObject.addProperty("date", saleList.getSaleDate());
                jsonObject.addProperty("customerName", saleList.getCustomerName());
                jsonObject.addProperty("code", saleListGoods.getGoodsCode());
                jsonObject.addProperty("name", saleListGoods.getGoodsName());
                jsonObject.addProperty("model", saleListGoods.getGoodsModel());
                jsonObject.addProperty("goodsType", goodsTypeDao.getGoodsTypeById(saleListGoods.getGoodsTypeId()).getGoodsTypeName());
                jsonObject.addProperty("unit", saleListGoods.getGoodsUnit());
                jsonObject.addProperty("price", saleListGoods.getPrice());
                jsonObject.addProperty("num", saleListGoods.getGoodsNum());
                jsonObject.addProperty("total", saleListGoods.getTotal());

                jsonArray.add(jsonObject);

            });
        });
        logService.save(new Log(Log.SELECT_ACTION, "销售统计"));
        return jsonArray.toString();
    }

    @Override
    public String getSaleDataByDay(String sTime, String eTime) {
        JsonArray jsonArray = new JsonArray();
        try {
            List<String> dateList = DateUtil.getTimeSlotByDay(sTime, eTime);
            List<SaleData> saleDataList = saleListGoodsDao.getSaleDataByDay(sTime, eTime);
            dateList.forEach(date -> {
                        JsonObject jsonObject = new JsonObject();
                        boolean flag = false;
                        for (int i = 0; i < saleDataList.size(); i++) {
                            SaleData saleData = saleDataList.get(i);
                            if (saleData.getDate().equals(date)) {
                                jsonObject.addProperty("date", saleData.getDate()); //日期

                                jsonObject.addProperty("saleTotal", BigDecimalUtil.keepTwoDecimalPlaces(saleData.getSaleTotal())); //销售总额

                                jsonObject.addProperty("purchasingTotal", BigDecimalUtil.keepTwoDecimalPlaces(saleData.getPurchasingTotal())); //成本总额

                                jsonObject.addProperty("profit", BigDecimalUtil.keepTwoDecimalPlaces(saleData.getSaleTotal() - saleData.getPurchasingTotal())); //利润

                                flag = true;

                                break;
                            }
                        }
                        if(!flag){
                            jsonObject.addProperty("date", date); //日期

                            jsonObject.addProperty("saleTotal", 0); //销售总额

                            jsonObject.addProperty("purchasingTotal", 0); //成本总额

                            jsonObject.addProperty("profit",0); //利润
                        }
                        jsonArray.add(jsonObject);
                    });
            logService.save(new Log(Log.SELECT_ACTION, "查询按日统计分析数据"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonArray.toString();
    }

    @Override
    public String getSaleDataByMonth(String sTime, String eTime) {
        JsonArray jsonArray = new JsonArray();
        try {
            List<String> dataList = DateUtil.getTimeSlotByMonth(sTime, eTime);
            List<SaleData> saleDataList = saleListGoodsDao.getSaleDataByMonth(sTime, eTime);
            dataList.forEach(date -> {
                JsonObject jsonObject = new JsonObject();
                boolean flag = false;
                for (int i = 0; i < saleDataList.size(); i++) {
                    SaleData saleData = saleDataList.get(i);
                    if (saleData.getDate().equals(date)) {
                        jsonObject.addProperty("date", saleData.getDate()); //日期

                        jsonObject.addProperty("saleTotal", BigDecimalUtil.keepTwoDecimalPlaces(saleData.getSaleTotal())); //销售总额

                        jsonObject.addProperty("purchasingTotal", BigDecimalUtil.keepTwoDecimalPlaces(saleData.getPurchasingTotal())); //成本总额

                        jsonObject.addProperty("profit", BigDecimalUtil.keepTwoDecimalPlaces(saleData.getSaleTotal() - saleData.getPurchasingTotal())); //利润

                        flag = true;

                        break;
                    }
                }
                if(!flag){
                    jsonObject.addProperty("date", date); //日期

                    jsonObject.addProperty("saleTotal", 0); //销售总额

                    jsonObject.addProperty("purchasingTotal", 0); //成本总额

                    jsonObject.addProperty("profit",0); //利润
                }
                jsonArray.add(jsonObject);
            });
            logService.save(new Log(Log.SELECT_ACTION, "查询按月统计分析数据"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonArray.toString();
    }
}
