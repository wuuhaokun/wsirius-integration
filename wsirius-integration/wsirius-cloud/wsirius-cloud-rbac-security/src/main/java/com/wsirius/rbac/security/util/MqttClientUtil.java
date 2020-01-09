package com.wsirius.rbac.security.util;

import cn.hutool.core.util.StrUtil;
import com.wsirius.rbac.security.common.Consts;
//import com.wsirius.rbac.security.common.Status;
import com.wsirius.rbac.security.config.MqttClientConfig;
import com.wsirius.rbac.security.vo.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;
import org.fusesource.mqtt.codec.MQTTFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.fusesource.hawtbuf.Buffer.utf8;

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

@Slf4j
public class MqttClientUtil {

    private long driverId;

    private long userId;

    private long orderId;

    //@Autowired
    private MqttClientConfig mqttClientConfig;

    private MQTT mqtt = null;
    /*final*/ CallbackConnection connection = null;

    public final static String systemAssign  = "SystemAssign";

    public final static String driverRespond  = "DriverRespond";
    public final static String passengerGetOn = "PassengerGetOn";
    public final static String startProcess = "StartProcess";
    public final static String endProcess = "EndProcess";
    public final static String cancelProcess = "CancelProcess";
    public final static String passengerGetOff = "PassengerGetOff";
    public final static String orderFinished = "OrderFinished";
    public final static String topics[] = {driverRespond,passengerGetOn,startProcess,endProcess,cancelProcess,passengerGetOff,orderFinished};

    public void setDriverId(long driverId){
        this.driverId = driverId;
    }

    public long getDriverId(){
        return this.driverId;
    }

    public void setUserId(long driverId){
        this.userId = userId;
    }

    public long getUserId(){
        return this.userId;
    }

    public void setOrderId(long orderIdId){
        this.orderId = orderIdId;
    }

    public long getOrderId(){
        return this.orderId;
    }

    public void setMqttClientConfig(MqttClientConfig mqttClientConfig){
        this.mqttClientConfig = mqttClientConfig;
    }

    public void startConnect(MyCallback myCallback) throws Exception {

        if(mqtt != null) {
            myCallback.callback(true);
            return;
        }
        mqtt = new MQTT();

        mqtt.setHost(mqttClientConfig.getUrl(), (int) mqttClientConfig.getPort());
        mqtt.setUserName(mqttClientConfig.getUsername());
        mqtt.setPassword(mqttClientConfig.getPassword());
        mqtt.setVersion("3.1.1");
//        mqtt.setCleanSession(false);
//        mqtt.setClientId((String) null);
        connection = mqtt.callbackConnection();
        final Promise<Buffer> result = new Promise<Buffer>();
        mqtt.setTracer(new Tracer(){
            @Override
            public void onReceive(MQTTFrame frame) {
                System.out.println("recv: "+frame);
            }

            @Override
            public void onSend(MQTTFrame frame) {
                System.out.println("send: "+frame);
            }

            @Override
            public void debug(String message, Object... args) {
                System.out.println(String.format("debug: "+message, args));
            }
        });

        // Start add a listener to process subscription messages, and start the
        // resume the connection so it starts receiving messages from the socket.
        connection.listener(new Listener() {
            public void onConnected() {
                System.out.println("connected");
            }

            public void onDisconnected() {
                System.out.println("disconnected");
            }

            public void onPublish(UTF8Buffer topic, Buffer payload, Runnable onComplete) {
                receiverHnader(topic, payload);
                result.onSuccess(payload);
                onComplete.run();
            }

            public void onFailure(Throwable value) {
                System.out.println("failure: "+value);
                result.onFailure(value);
                connection.disconnect(null);
            }
        });

        connection.connect(new Callback<Void>() {
            // Once we connect..
            public void onSuccess(Void v) {
                System.out.println("onSuccess ");
                myCallback.callback(true);
                //connection.publish("aaa", "qqq".getBytes(), QoS.AT_LEAST_ONCE, true, null);
            }
            public void onFailure(Throwable value) {
                result.onFailure(value);
                myCallback.callback(false);
            }
        });

        //assertEquals("Hello", new String(result.await().toByteArray()));
    }

    public void Subscribe(Topic[] topics){
        final Promise<Buffer> result = new Promise<Buffer>();
        connection.subscribe(topics, new Callback<byte[]>() {
            public void onSuccess(byte[] value) {
                System.out.println("onSuccess: ");
            }

            public void onFailure(Throwable value) {
                result.onFailure(value);
                connection.disconnect(null);
            }
        });
    }

    public void publish(String topic, String message ) {
        // Once subscribed, publish a message on the same topic.
        connection.publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE, false, null);
    }

    public void subscribes() {
        for(int i = 0; i < topics.length; i ++){
            String topic = topics[i];
            Topic[] topics = {new Topic(utf8(mqttCommandEncode(topic)), QoS.AT_LEAST_ONCE)};
            this.Subscribe(topics);
        }
    }

    private String mqttCommandEncode(String key){
        return String.format("T=%s&Id=%d",key,this.driverId);
    }

    private String mqttCommandDecode(String key){
        return String.format("T=%s&Id=%d",key,this.driverId);
    }

    private void receiverHnader(UTF8Buffer topic, Buffer payload){
        if(topic.toString() == mqttCommandEncode(driverRespond)){
            System.out.println("訂單己成立");
           //訂單己成立
        }
        else if(topic.toString() == mqttCommandEncode(passengerGetOn)){
            System.out.println("乘客己上車");
            //
        }
        else if(topic.toString() == mqttCommandEncode(startProcess)){
            System.out.println("開始行程");
        }
        else if(topic.toString() == mqttCommandEncode(endProcess)){
            System.out.println("行程結束");
        }
        else if(topic.toString() == mqttCommandEncode(cancelProcess)){
            System.out.println("取消行程");
        }
        else if(topic.toString() == mqttCommandEncode(passengerGetOff)){
            System.out.println("乘客下車");
        }
        else if(topic.toString() == mqttCommandEncode(orderFinished)){
            System.out.println("訂單己完成");
        }
    }

    public void AssignCar(){
        this.publish(mqttCommandEncode(systemAssign),"Assign Car");
    }

}
