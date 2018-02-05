/**
 * aliyun.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package client.socket.li.com.sct_client.util;

import android.util.Log;

import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import client.socket.li.com.sct_client.App;
import client.socket.li.com.sct_client.FileUtils;
import client.socket.li.com.sct_client.bean.PayloadBean;

/**
 * IoT套件JAVA版设备接入demo
 */
public class SimpleClient4IOT {

    /******这里是客户端需要的参数*******/


    //用于测试的topic
    private  String subTopic = "/" + Const.PRODUCT_KEY + "/" + App.sApp.getLocalDeviceName()+ "/regeister";
    private  String pubTopic = "/" + Const.PRODUCT_KEY  + "/" + App.sApp.getLocalDeviceName() + "/sendMessage";

/*    public static void main(String... strings) throws Exception {
        //客户端设备自己的一个标记，建议是MAC或SN，不能为空，32字符内
        String clientId = InetAddress.getLocalHost().getHostAddress();

        //设备认证
        Map<String, String> params = new HashMap<String, String>();
        params.put("productKey", productKey); //这个是对应用户在控制台注册的 设备productkey
        params.put("deviceName", deviceName); //这个是对应用户在控制台注册的 设备name
        params.put("clientId", clientId);
        String t = System.currentTimeMillis() + "";
        params.put("timestamp", t);

        //MQTT服务器地址，TLS连接使用ssl开头
        String targetServer = "ssl://" + productKey + ".iot-as-mqtt.cn-shanghai.aliyuncs.com:1883";

        //客户端ID格式，两个||之间的内容为设备端自定义的标记，字符范围[0-9][a-z][A-Z]
        String mqttclientId = clientId + "|securemode=2,signmethod=hmacsha1,timestamp=" + t + "|";
        String mqttUsername = deviceName + "&" + productKey; //mqtt用户名格式
        String mqttPassword = SignUtil.sign(params, secret, "hmacsha1"); //签名

        System.err.println("mqttclientId=" + mqttclientId);

        connectMqtt(targetServer, mqttclientId, mqttUsername, mqttPassword, deviceName);
    }*/

    public  MqttAndroidClient sampleClient=null;
    public  void connectMqttAndroid(String url, final String clientId, String mqttUsername, String mqttPassword, final String deviceName) throws Exception {
        MemoryPersistence persistence = new MemoryPersistence();
        SSLSocketFactory socketFactory = createSSLSocket();
         sampleClient = new MqttAndroidClient(App.sApp, url, clientId, persistence);

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setMqttVersion(4); // MQTT 3.1.1
        connOpts.setSocketFactory(socketFactory);

        //设置是否自动重连
        connOpts.setAutomaticReconnect(true);

        //如果是true，那么清理所有离线消息，即QoS1或者2的所有未接收内容
        connOpts.setCleanSession(false);

        connOpts.setUserName(mqttUsername);
        connOpts.setPassword(mqttPassword.toCharArray());
        connOpts.setKeepAliveInterval(180);

        LogUtil.print(clientId + "进行连接, 目的地: " + url);

        sampleClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                LogUtil.print("连接失败,原因:" + cause+"    "+onDeviceStateLinstenner);
                if(null != onDeviceStateLinstenner){
                    onDeviceStateLinstenner.localOffline();
                }
                cause.printStackTrace();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String receve=new String(message.getPayload(), "UTF-8");
                LogUtil.print("接收到消息,来至Topic [" + topic + "] , 内容是:["
                        + receve + "],  ");
                PayloadBean payloadBean=new Gson().fromJson(receve,PayloadBean.class);
                Log.d("LMW",payloadBean+" "+onDeviceStateLinstenner);
                if(!App.sApp.getLocalDeviceName().equals(payloadBean.getDeviceName())){
                    if(App.sApp.getLocalDeviceName().contains("uee")&&payloadBean.getDeviceName().contains("uee")){
                        return;
                    }
                    if("online".equals(payloadBean.getStatus())){
                        if(null != onDeviceStateLinstenner){
                            onDeviceStateLinstenner.onLine(payloadBean);
                        }
                    }else{
                        if(null != onDeviceStateLinstenner){
                            onDeviceStateLinstenner.deviceOffLine(null,payloadBean);
                        }
                    }
                }else{
                    if("online".equals(payloadBean.getStatus())) {
                        if (null != onDeviceStateLinstenner) {
                            onDeviceStateLinstenner.localOnline(payloadBean);
                        }
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                //如果是QoS0的消息，token.resp是没有回复的
                LogUtil.print("消息发送成功! " + ((token == null || token.getResponse() == null) ? "null"
                        : token.getResponse().getKey()));
                LogUtil.print("getClientId "+sampleClient.getClientId());
            }
        });
        try {
            //addToHistory("Connecting to " + serverUri);
            sampleClient.connect(connOpts, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    if(null != onDeviceStateLinstenner){
                        onDeviceStateLinstenner.deviceConnectionSuccess(asyncActionToken);
                    }
                    LogUtil.print("连接成功:--- asyncActionToken "+asyncActionToken);
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    sampleClient.setBufferOpts(disconnectedBufferOptions);

                    try {

                        //这里测试发送一条消息
                        String content = "{'content':'msg from :" + clientId + "," + System.currentTimeMillis() + "'}";

                        MqttMessage message = new MqttMessage(content.getBytes("utf-8"));
                        message.setQos(0);
                        //System.out.println(System.currentTimeMillis() + "消息发布:---");
                        sampleClient.publish(pubTopic, message);

                        //回复RRPC响应
                        final ExecutorService executorService = new ThreadPoolExecutor(2, 4, 600, TimeUnit.SECONDS,
                                new ArrayBlockingQueue<Runnable>(100), new CallerRunsPolicy());

                        String reqTopic = "/sys/" + Const.PRODUCT_KEY + "/" + deviceName + "/rrpc/request/+";

                        sampleClient.subscribe(subTopic, 0, null, new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            LogUtil.print("subscribe：" + "onSuccess" + ", asyncActionToken=" + asyncActionToken);

                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            LogUtil.print("subscribe：" + "onFailure" + ", asyncActionToken=" + asyncActionToken);
                            exception.printStackTrace();
                        }
                    });
                        // THIS DOES NOT WORK!
                        /*sampleClient.subscribe(subTopic, 0, new IMqttMessageListener() {
                            @Override
                            public void messageArrived(String topic, MqttMessage message) throws Exception {

                                LogUtil.print("收到请求：" + message + ", topic=" + topic);
                                String messageId = topic.substring(topic.lastIndexOf('/') + 1);
                                final String respTopic = "/sys/" + productKey + "/" + deviceName + "/rrpc/response/" + messageId;
                                String content = "hello world";
                                final MqttMessage response = new MqttMessage(content.getBytes());
                                response.setQos(0); //RRPC只支持QoS0
                                //不能在回调线程中调用publish，会阻塞线程，所以使用线程池
                                executorService.submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            sampleClient.publish(respTopic, response);
                                            LogUtil.print("回复响应成功，topic=" + respTopic);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                // message Arrived!
                                Log.e("mqtt", "Message: " + topic + " : " + new String(message.getPayload()));
                            }
                        });*/
                    }
                    catch (MqttException ex) {
                        Log.e("mqtt", "Exception whilst subscribing");

                        ex.printStackTrace();
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    if(null != onDeviceStateLinstenner){
                        onDeviceStateLinstenner.deviceConnectionFailure(asyncActionToken,exception);
                    }
                    LogUtil.print("连接失败:--- asyncActionToken"+asyncActionToken +"    "+exception.getMessage());
                    exception.printStackTrace();
                }
            });
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    private  SSLSocketFactory createSSLSocket() throws Exception {
        SSLContext context = SSLContext.getInstance("TLSV1.2");
        context.init(null, new TrustManager[]{new ALiyunIotX509TrustManager()}, null);
        SSLSocketFactory socketFactory = context.getSocketFactory();
        return socketFactory;
    }

    public OnDeviceStateLinstenner onDeviceStateLinstenner;
    public interface OnDeviceStateLinstenner{
        void deviceOffLine(Throwable cause,PayloadBean payloadBean);
        void onLine(PayloadBean payloadBean);
        void localOnline(PayloadBean payloadBean);
        void localOffline();
        void deviceConnectionSuccess(IMqttToken asyncActionToken);
        void deviceConnectionFailure(IMqttToken asyncActionToken, Throwable exception);

    }

    public  void setOnDeviceStateLinstenner(OnDeviceStateLinstenner onDeviceStateLinstenner) {
        this.onDeviceStateLinstenner = onDeviceStateLinstenner;
    }
}
