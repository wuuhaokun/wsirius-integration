package com.wsirius.rbac.security.util;

import com.wsirius.core.util.Results;
import com.wsirius.rbac.security.config.MqttClientConfig;
import com.wsirius.rbac.security.domain.entity.VehicleInfo;
import com.wsirius.rbac.security.domain.service.*;
import com.wsirius.rbac.security.payload.CallTaxiRequest;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.*;

import static org.fusesource.hawtbuf.Buffer.utf8;
import com.wsirius.rbac.security.util.MyCallback;
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
@EnableConfigurationProperties(MqttClientConfig.class)
@Configuration
@Slf4j
public class DispatchCarUtil {
    @Autowired
    private UserService userService;

    @Autowired
    private MqttClientConfig mqttClientConfig;

    @Autowired
    private VehicleInfoService vehicleInfoService;

    private Map<String, MqttClientUtil> mqttMap = new HashMap<>();

    private double distance = 0;

    public boolean searchCar(CallTaxiRequest callTaxiRequest) throws Exception {
        List<VehicleInfo> vehicleInfoList = vehicleInfoService.selectAll();
        if(vehicleInfoList.size() <= 0){
            //return Results.failure("附近沒有空車！！");
            return false;
        }

        // List 自訂排序
        Collections.sort( vehicleInfoList, new Comparator<VehicleInfo>(){
            public int compare( VehicleInfo l1, VehicleInfo l2 ) {
                double distance1 = GeoUtils.getDistance(callTaxiRequest.getStartLatitude(), callTaxiRequest.getStartLongitude(), l1.getLatitude(), l1.getLongitude());
                double distance2 = GeoUtils.getDistance(callTaxiRequest.getStartLatitude(), callTaxiRequest.getStartLongitude(), l2.getLatitude(), l2.getLongitude());
                if (distance2 > distance1){
                    distance = distance1;
                    return 1;
                }
                distance = distance2;
                return -1;
            }
        });

        if(distance > 8.0) {
            //return Results.failure("附近沒有空車！！");
            return false;
        }
        Object[] objs = vehicleInfoList.toArray();
        VehicleInfo info = vehicleInfoList.get(0);
        return true;
    }

    public boolean AssignCar(long driverId/*, long userId*/,long orderId) throws Exception {
        String mqqtKey = String.format("Type=Assign&Id=%d",driverId);
        String mapKry = String.format("%s",driverId);
        if(!mqttMap.containsKey(mapKry)){
            MqttClientUtil mqttClientUtil = new MqttClientUtil();
            mqttClientUtil.setMqttClientConfig(mqttClientConfig);
            mqttMap.put(mapKry, mqttClientUtil);
        }
        MqttClientUtil mqttClientUtil = mqttMap.get(mapKry);
        mqttClientUtil.startConnect(new MyCallback(){
            public void callback(Boolean success){
                mqttClientUtil.AssignCar();
                mqttClientUtil.subscribes();
            }
        });
        return true;
    }


//    public void subscribes() {
//        String assignTopic = String.format("Type=Assign&Id=%d",driverId);
//        Topic[] topics1 = {new Topic(utf8(assignTopic), QoS.AT_LEAST_ONCE)};
//
//        String passengerGetOnTopic = String.format("Type=PassengerGetOn&Id=%d",driverId);
//        Topic[] topics2 = {new Topic(utf8(passengerGetOnTopic), QoS.AT_LEAST_ONCE)};
//
//        String startTopic = String.format("Type=Start&Id=%d",driverId);
//        Topic[] topics3 = {new Topic(utf8(startTopic), QoS.AT_LEAST_ONCE)};
//
//        String endTopic = String.format("Type=End&Id=%d",driverId);
//        Topic[] topics4 = {new Topic(utf8(endTopic), QoS.AT_LEAST_ONCE)};
//
//        String cancelTopic = String.format("Type=Cancel&Id=%d",driverId);
//        Topic[] topics5 = {new Topic(utf8(cancelTopic), QoS.AT_LEAST_ONCE)};
//
//        String passengerGetOffTopic = String.format("Type=PassengerGetOff&Id=%d",driverId);
//        Topic[] topics6 = {new Topic(utf8(passengerGetOffTopic), QoS.AT_LEAST_ONCE)};
//
//        String orderFinishedTopic = String.format("Type=OrderFinished&Id=%d",driverId);
//        Topic[] topics7 = {new Topic(utf8(orderFinishedTopic), QoS.AT_LEAST_ONCE)};
//
//        this.Subscribe(topics1);
//        this.Subscribe(topics2);
//        this.Subscribe(topics3);
//        this.Subscribe(topics4);
//        this.Subscribe(topics5);
//        this.Subscribe(topics6);
//        this.Subscribe(topics7);
//    }


//    void subscribes(){
//
//    }
}
