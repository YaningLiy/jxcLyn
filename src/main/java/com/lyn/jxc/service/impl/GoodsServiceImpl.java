package com.lyn.jxc.service.impl;

import com.lyn.jxc.dao.GoodsDao;
import com.lyn.jxc.domain.ErrorCode;
import com.lyn.jxc.domain.ServiceVO;
import com.lyn.jxc.domain.SuccessCode;
import com.lyn.jxc.entity.Goods;
import com.lyn.jxc.entity.Log;
import com.lyn.jxc.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    LogService logService;

    @Resource
    GoodsDao goodsDao;
    @Resource
    SaleListGoodsService saleListGoodsService;
    @Resource
    CustomerReturnListGoodsService customerReturnListGoodsService;
    @Resource
    PurchaseListGoodsService purchaseListGoodsService;


    @Override
    public Map<String, Object> listInventory(Integer page, Integer rows, String codeOrName, Integer goodsTypeId) {
        if (page == 0) {
            page = 1;
        }
        Integer offset = (page - 1) * rows;
        List<Goods> goodsList = goodsDao.listInventory(offset, rows, codeOrName, goodsTypeId);
        goodsList.forEach(goods -> {
            goods.setSaleTotal(saleListGoodsService.getSaleTotalByGoodsId(goods.getGoodsId()) - customerReturnListGoodsService.getCustomerReturnTotalByGoodsId(goods.getGoodsId()));
        });
        Map<String, Object> map = new HashMap<>();
        Integer total = goodsDao.getGoodsInventoryCount(codeOrName, goodsTypeId);
        map.put("total", total);
        map.put("rows", goodsList);
        logService.save(new Log(Log.SELECT_ACTION, "分页查询商品库存信息"));
        return map;
    }


    @Override
    public Map<String, Object> list(Integer page, Integer rows, String name, Integer goodsTypeId) {
        if (page == 0) {
            page = 1;
        }
        Integer offset = (page - 1) * rows;
        List<Goods> goodsList = goodsDao.list(offset, rows, name, goodsTypeId);

        Map<String, Object> map = new HashMap<>();
        Integer total = goodsDao.getGoodsCount(name, goodsTypeId);
        map.put("total", total);
        map.put("rows", goodsList);
        logService.save(new Log(Log.SELECT_ACTION, "查询所有商品信息"));
        return map;
    }

    @Override
    public void save(Goods goods) {
        if (goods.getGoodsId() == null) {
            logService.save(new Log(Log.INSERT_ACTION, "添加商品：" + goods.getGoodsName()));
            goods.setInventoryQuantity(0);
            goods.setState(0);
            goods.setLastPurchasingPrice(goods.getPurchasingPrice());
            goodsDao.insert(goods);
        } else {
            goodsDao.update(goods);
            logService.save(new Log(Log.INSERT_ACTION, "修改商品：" + goods.getGoodsName()));
        }
    }

    @Override
    public ServiceVO delete(Integer goodsId) {
        Goods goods = goodsDao.getGoods(goodsId);
        if (goods == null) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
        if (goods.getState() == 1) {
            return new ServiceVO(ErrorCode.STORED_ERROR_CODE, ErrorCode.STORED_ERROR_MESS);
        } else if (goods.getState() == 2) {
            return new ServiceVO(ErrorCode.HAS_FORM_ERROR_CODE, ErrorCode.HAS_FORM_ERROR_MESS);
        } else {
            logService.save(new Log(Log.DELETE_ACTION, "删除商品：" + goods.getGoodsName()));
            goodsDao.delete(goodsId);
        }

        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> getNoInventoryQuantity(Integer page, Integer rows, String nameOrCode) {
        if (page == 0) {
            page = 1;
        }
        Integer offset = (page - 1) * rows;
        List<Goods> goodsList = goodsDao.getNoInventoryQuantity(offset, rows, nameOrCode);
        Map<String, Object> map = new HashMap<>();
        Integer total = goodsDao.getNoInventoryQuantityCount(nameOrCode);
        map.put("total", total);
        map.put("rows", goodsList);
        logService.save(new Log(Log.SELECT_ACTION, "分页查询商品库存信息"));
        return map;
    }

    @Override
    public Map<String, Object> getHasInventoryQuantity(Integer page, Integer rows, String nameOrCode) {
        if (page == 0) {
            page = 1;
        }
        Integer offset = (page - 1) * rows;
        List<Goods> goodsList = goodsDao.getHasInventoryQuantity(offset, rows, nameOrCode);
        Map<String, Object> map = new HashMap<>();
        Integer total = goodsDao.getHasInventoryQuantityCount(nameOrCode);
        map.put("total", total);
        map.put("rows", goodsList);
        logService.save(new Log(Log.SELECT_ACTION, "分页查询商品库存信息"));
        return map;
    }

    @Override
    public ServiceVO saveStock(Integer goodsId, Integer inventoryQuantity, Double purchasingPrice) {
        Goods goods = goodsDao.getGoods(goodsId);
        goods.setInventoryQuantity(inventoryQuantity);
        goods.setPurchasingPrice(purchasingPrice);
        goods.setLastPurchasingPrice(purchasingPrice);
        logService.save(new Log(Log.UPDATE_ACTION, goods.getGoodsName() + "商品期初入库"));
        goodsDao.update(goods);
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public ServiceVO deleteStock(Integer goodsId) {
        Goods goods = goodsDao.getGoods(goodsId);
        if (goods == null) {
            return new ServiceVO(ErrorCode.NULL_POINTER_CODE, ErrorCode.NULL_POINTER_MESS);
        }
        if (goods.getState() == 2) {
            return new ServiceVO(ErrorCode.HAS_FORM_ERROR_CODE, ErrorCode.HAS_FORM_ERROR_MESS);
        } else {
            logService.save(new Log(Log.UPDATE_ACTION, "清空商品库存：" + goods.getGoodsName()));
            goods.setInventoryQuantity(0);
            goodsDao.update(goods);
        }

        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> listAlarm() {
        List<Goods> goodsList = goodsDao.ltMinNumGoodsList();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",goodsList);
        logService.save(new Log(Log.SELECT_ACTION, "查询库存报警商品信息"));
        return map;
    }


}
