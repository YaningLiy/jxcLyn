package com.lyn.jxc.vo;

import com.lyn.jxc.entity.GoodsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsTypeVo extends GoodsType {
    Integer id;
    String text;
    String state;
    String iconCls;

    List<GoodsTypeVo> children;

}
