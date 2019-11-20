package com.wsirius.rbac.security.util;

import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * JWT 工具类
 * </p>
 *
 * @package: com.xkcoding.rbac.security.util
 * @description: JWT 工具类
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 13:42
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */

//public class DistCnvterUtil {
//    private final static double PI = 3.14159265358979323; // 圓周率
//    private final static double R = 6371229; // 地球的半徑
//
//    public static double getDistance(double longt1, double lat1, double longt2,double lat2) {
//        double x, y, distance;
//        x = (longt2 - longt1) * PI * R
//                * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
//        y = (lat2 - lat1) * PI * R / 180;
//        distance = Math.hypot(x, y);
//        return distance;
//    }
//}

//package com.jttx.poi.utils;
//import com.jttx.poi.entity.Point;
/**
 * Created by louis on 2014/9/2.
 */
@Configuration
public class GeoUtils {
    /**
     * 地球半徑
     */
    private static double EarthRadius = 6378.137;

    /**
     * 經緯度轉化成弧度
     * Add by 成長的小豬（Jason.Song） on 2017/11/01
     * http://blog.csdn.net/jasonsong2008
     *
     * @param d 經度/緯度
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 計算兩個座標點之間的距離
     * Add by 成長的小豬（Jason.Song） on 2017/11/01
     * http://blog.csdn.net/jasonsong2008
     *
     * @param firstLatitude   第一個座標的緯度
     * @param firstLongitude  第一個座標的經度
     * @param secondLatitude  第二個座標的緯度
     * @param secondLongitude 第二個座標的經度
     * @return 返回兩點之間的距離，單位：公里/千米
     */
    public static double getDistance(double firstLatitude, double firstLongitude,
                                     double secondLatitude, double secondLongitude) {
        double firstRadLat = rad(firstLatitude);
        double firstRadLng = rad(firstLongitude);
        double secondRadLat = rad(secondLatitude);
        double secondRadLng = rad(secondLongitude);

        double a = firstRadLat - secondRadLat;
        double b = firstRadLng - secondRadLng;
        double cal = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(firstRadLat)
                * Math.cos(secondRadLat) * Math.pow(Math.sin(b / 2), 2))) * EarthRadius;
        double result = Math.round(cal * 10000d) / 10000d;
        return result;
    }

    /**
     * 計算兩個座標點之間的距離
     * Add by 成長的小豬（Jason.Song） on 2017/11/01
     * http://blog.csdn.net/jasonsong2008
     *
     * @param firstPoint  第一個座標點的（緯度,經度） 例如："31.2553210000,121.4620020000"
     * @param secondPoint 第二個座標點的（緯度,經度） 例如："31.2005470000,121.3269970000"
     * @return 返回兩點之間的距離，單位：公里/千米
     */
    public static double GetPointDistance(String firstPoint, String secondPoint) {
        String[] firstArray = firstPoint.split(",");
        String[] secondArray = secondPoint.split(",");
        double firstLatitude = Double.valueOf(firstArray[0].trim());
        double firstLongitude = Double.valueOf(firstArray[1].trim());
        double secondLatitude = Double.valueOf(secondArray[0].trim());
        double secondLongitude = Double.valueOf(secondArray[1].trim());
        return getDistance(firstLatitude, firstLongitude, secondLatitude, secondLongitude);
    }

}