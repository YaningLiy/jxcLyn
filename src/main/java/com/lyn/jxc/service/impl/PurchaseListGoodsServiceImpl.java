package com.lyn.jxc.service.impl;

import com.lyn.jxc.dao.*;
import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;
import com.lyn.jxc.entity.*;
import com.lyn.jxc.service.LogService;
import com.lyn.jxc.service.PurchaseListGoodsService;
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
public class PurchaseListGoodsServiceImpl implements PurchaseListGoodsService {

    @Resource
    PurchaseListGoodsDao purchaseListGoodsDao;
    @Resource
    PurchaseListDao purchaseListDao;
    @Resource
    UserDao userDao;
    @Resource
    LogService logService;
    @Resource
    GoodsDao goodsDao;

    @Resource
    GoodsTypeDao goodsTypeDao;


    @Override
    public ServiceVO save(PurchaseList purchaseList, String purchaseListGoodsStr) {
        List<PurchaseListGoods> purchaseListGoodsList = new Gson().fromJson(purchaseListGoodsStr, new TypeToken<List<PurchaseListGoods>>() {
        }.getType());
        User user = userDao.findUserByName((String) SecurityUtils.getSubject().getPrincipal());
        purchaseList.setUserId(user.getUserId());
        purchaseListDao.insert(purchaseList);
        purchaseListGoodsList.forEach(purchaseListGoods -> {
            purchaseListGoods.setPurchaseListId(purchaseList.getPurchaseListId());
            purchaseListGoodsDao.insert(purchaseListGoods);
            Goods goods = goodsDao.getGoods(purchaseListGoods.getGoodsId());
            goods.setInventoryQuantity(goods.getInventoryQuantity() + purchaseListGoods.getGoodsNum());
            goods.setLastPurchasingPrice(purchaseListGoods.getPrice());
            goods.setPurchasingPrice((goods.getPurchasingPrice() + purchaseListGoods.getPrice()) / 2);
            goods.setState(2);
            goodsDao.update(goods);
        });
        logService.save(new Log(Log.INSERT_ACTION, "进货单保存:" + purchaseList.getPurchaseListId()));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> list(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime) {
        List<PurchaseList> purchaseList = purchaseListDao.getpurchaseList(purchaseNumber, supplierId, state, sTime, eTime);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", purchaseList);
        logService.save(new Log(Log.SELECT_ACTION, "退货单列表展示"));
        return map;
    }

    @Override
    public Map<String, Object> goodsList(Integer purchaseListId) {
        List<PurchaseListGoods> purchaseListGoodsList = purchaseListGoodsDao.getPurchaseListGoods(purchaseListId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", purchaseListGoodsList);
        logService.save(new Log(Log.SELECT_ACTION, "客户退货单查询"));
        return map;
    }

    @Override
    public ServiceVO delete(Integer purchaseListId) {
        purchaseListGoodsDao.deleteBypurchaseListId(purchaseListId);
        purchaseListDao.deleteByPurchaseListId(purchaseListId);
        logService.save(new Log(Log.DELETE_ACTION, "退货单商品信息"));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public ServiceVO updateState(Integer purchaseListId) {
        purchaseListDao.updateStateByPurchaseListId(purchaseListId);
        logService.save(new Log(Log.UPDATE_ACTION, "支付结算（修改进货单付款状态）:" + purchaseListId));
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        JsonArray jsonArray = new JsonArray();
        List<PurchaseList> purchaseListList = purchaseListDao.getpurchaseList(null, null, null, sTime, eTime);
        purchaseListList.forEach(purchaseList -> {
            List<PurchaseListGoods> purchaseListGoodsLIst = purchaseListGoodsDao.getPurchaseListGoodsByGoodsTypeIdAndCodeOrName(purchaseList.getPurchaseListId(), goodsTypeId, codeOrName);
            purchaseListGoodsLIst.forEach(purchaseListGoods -> {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("number",purchaseList.getPurchaseNumber());
                jsonObject.addProperty("date",purchaseList.getPurchaseDate());
                jsonObject.addProperty("supplierName",purchaseList.getSupplierName());
                jsonObject.addProperty("code",purchaseListGoods.getGoodsCode());
                jsonObject.addProperty("name",purchaseListGoods.getGoodsName());
                jsonObject.addProperty("model",purchaseListGoods.getGoodsModel());
                jsonObject.addProperty("goodsType",goodsTypeDao.getGoodsTypeById(purchaseListGoods.getGoodsTypeId()).getGoodsTypeName());
                jsonObject.addProperty("unit",purchaseListGoods.getGoodsUnit());
                jsonObject.addProperty("price",purchaseListGoods.getPrice());
                jsonObject.addProperty("num",purchaseListGoods.getGoodsNum());
                jsonObject.addProperty("total",purchaseListGoods.getTotal());

                jsonArray.add(jsonObject);

            });
        });
        logService.save(new Log(Log.SELECT_ACTION, "进货商品统计查询"));
        return jsonArray.toString();
    }
}
