package com.lyn.jxc.service.impl;

import com.lyn.jxc.dao.GoodsTypeDao;
import com.lyn.jxc.entity.GoodsType;
import com.lyn.jxc.entity.Log;
import com.lyn.jxc.service.GoodsTypeService;
import com.lyn.jxc.service.LogService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Resource
    GoodsTypeDao goodsTypeDao;
    @Resource
    LogService logService;

    @Override
    public String loadGoodsType() {
        logService.save(new Log(Log.SELECT_ACTION, "查询商品类信息"));
        return this.getAllGoodsType(-1).toString();
    }

    @Override
    public void save(String goodsTypeName, Integer pId) {
        GoodsType goodsType = new GoodsType(goodsTypeName, 0, pId);
        goodsTypeDao.save(goodsType);
    }

    @Override
    public void delete(Integer goodsTypeId) {
        goodsTypeDao.delete(goodsTypeId);
        logService.save(new Log(Log.DELETE_ACTION, "删除商品类信息"));
    }

    public JsonArray getAllGoodsType(Integer parentId) {
        JsonArray array = this.getGoodSTypeByParentId(parentId);
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = (JsonObject) array.get(i);
            if (obj.get("state").getAsString().equals("open")) {
                continue;
            } else {
                obj.add("children", this.getAllGoodsType(obj.get("id").getAsInt()));
            }
        }
        return array;
    }

    public JsonArray getGoodSTypeByParentId(Integer parentId) {
        JsonArray jsonArray = new JsonArray();
        List<GoodsType> goodsTypeList = goodsTypeDao.getAllGoodsTypeByParentId(parentId);
        for (GoodsType goodsType : goodsTypeList) {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", goodsType.getGoodsTypeId());
            obj.addProperty("text", goodsType.getGoodsTypeName());
            if (goodsType.getGoodsTypeState() == 1) {
                obj.addProperty("state", "closed");
            } else {
                obj.addProperty("state", "open");
            }
            obj.addProperty("iconCls", "goods-type");
            JsonObject attributes = new JsonObject();
            attributes.addProperty("state", goodsType.getGoodsTypeState());
            obj.add("attributes", attributes);
            jsonArray.add(obj);
        }
        return jsonArray;
    }


}
