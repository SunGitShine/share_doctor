package com.phicomm.doctor.util;

import java.util.HashMap;
import java.util.Map;

public class ChainAreaUtil {
	
	public static Integer provinceToArea(String province) {
		return makeChainArea().get(province);
	}

	public static Map<String, Integer> makeChainArea(){
		
		//1、华东地区， 2、华南地区， 3：华中地区 ， 4：华北地区， 5：西北地区， 6：西南地区， 7：东北地区， 8：台港澳地区
		Map<String, Integer> areaMap = new HashMap<String, Integer>();
		//1华东地区
		areaMap.put("山东省", 1);
		areaMap.put("江苏省", 1);
		areaMap.put("安徽省", 1);
		areaMap.put("浙江省", 1);
		areaMap.put("福建省", 1);
		areaMap.put("上海市", 1);
		//2华南地区
		areaMap.put("广东省", 2);
		areaMap.put("广西壮族自治区", 2);
		areaMap.put("海南省", 2);
		//3华中地区
		areaMap.put("湖北省", 3);
		areaMap.put("湖南省", 3);
		areaMap.put("河南省", 3);
		areaMap.put("江西省", 3);
		//4华北地区
		areaMap.put("北京市", 4);
		areaMap.put("天津市", 4);
		areaMap.put("河北省", 4);
		areaMap.put("山西省", 4);
		areaMap.put("内蒙古自治区", 4);
		//5西北地区
		areaMap.put("宁夏回族自治区", 5);
		areaMap.put("新疆维吾尔族自治区", 5);
		areaMap.put("青海省", 5);
		areaMap.put("陕西省", 5);
		areaMap.put("甘肃省", 5);
		//6西南地区
		areaMap.put("四川省", 6);
		areaMap.put("云南省", 6);
		areaMap.put("贵州省", 6);
		areaMap.put("西藏自治区", 6);
		areaMap.put("重庆市", 6);
		//7东北地区
		areaMap.put("辽宁省", 7);
		areaMap.put("吉林省", 7);
		areaMap.put("黑龙江省", 7);
		//8台港澳地区
		areaMap.put("台湾", 8);
		areaMap.put("香港", 8);
		areaMap.put("澳门", 8);
		return areaMap;
	}
}
