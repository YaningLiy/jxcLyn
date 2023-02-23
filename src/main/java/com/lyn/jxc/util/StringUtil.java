package com.lyn.jxc.util;

/**
 * @description 字符串工具类
 */
public class StringUtil {

    /**
     * 判断是否是空
     * @param str
     * @return
     * @athor b3
     */
    public static boolean isEmpty(String str){
        if(str==null||"".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断是否不是空
     * @param str
     * @return b2
     * @return
     * @author b1
     */
    public static boolean isNotEmpty(String str){
        if((str!=null)&&!"".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }

}
