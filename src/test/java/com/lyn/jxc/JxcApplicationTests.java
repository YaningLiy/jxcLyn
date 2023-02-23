package com.lyn.jxc;

import com.lyn.jxc.dao.GoodsDao;
import com.lyn.jxc.dao.GoodsTypeDao;
import com.lyn.jxc.entity.Goods;
import com.lyn.jxc.entity.GoodsType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JxcApplicationTests {
	@Resource
	GoodsTypeDao goodsTypeDao;
	@Resource
	GoodsDao goodsDao;
	@Test
	public void contextLoads() {
		List<GoodsType> goodsTypeList = goodsTypeDao.loadGoodsType();
		System.out.println(goodsTypeList);
	}
	@Test
	public void contextLoads2() {
		List<Goods> goods_name = goodsDao.ltMinNumGoodsListTest("t_goods ");
		System.out.println(goods_name);

	}

}
