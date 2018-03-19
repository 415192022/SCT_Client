package client.socket.li.com.sct_client;

import android.app.Application;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import client.socket.li.com.sct_client.bean.PayloadBean;
import client.socket.li.com.sct_client.bean.QueryDeviceIsExistBean;
import client.socket.li.com.sct_client.bean.QueryPhoneNumberBean;
import client.socket.li.com.sct_client.model.AliIotImpl;
import client.socket.li.com.sct_client.util.Const;
import client.socket.li.com.sct_client.util.SimpleClient4IOT;

/**
 * Created by Zan on 2017/12/18 0018.
 * App.
 */

public class App extends Application {
    public static App sApp;
    int index = 0;
    int random=new Random().nextInt(100000);

    private static String localDeviceName;
    private static File localDeviceNameFile=new File(Environment.getExternalStorageDirectory()+"/devicename");

    public void setLocalDeviceName(String localDeviceName) {
        App.localDeviceName = localDeviceName;
    }


    public String getLocalDeviceName() {
        return localDeviceName;
    }

    public File getLocalDeviceNameFile() {
        return localDeviceNameFile;
    }

    public static void setLocalDeviceNameFile(File localDeviceNameFile) {
        App.localDeviceNameFile = localDeviceNameFile;
    }

    public static String SOCKET_PATH = Environment.getExternalStorageDirectory().getPath();
    ;

    //    public int type = 0;    //mao
//    public int type = 1;    //mei
    public int type = 2;      //mi

    public static int SECOND_SOCKET;
    private boolean mMao;


    @Override
    public void onCreate() {
        super.onCreate();

        init();
        sApp = this;
        Log.e("zan - > ", SOCKET_PATH);
        if (isMao()) {
            SECOND_SOCKET = 60;
        } else {
            SECOND_SOCKET = 60;
        }
        if(FileUtils.isExsitFile(localDeviceNameFile)){
            String conten=FileUtils.readFile(localDeviceNameFile);
            Log.d("LMW",conten+"=====");
            setLocalDeviceName(conten);
        }else{
            try {
                localDeviceNameFile.createNewFile();
                setLocalDeviceNameFile(localDeviceNameFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static Socket socket;


    /**
     * 建立服务端连接
     */
    public void conn(final String ip, final int port) {
        new Thread() {

            @Override
            public void run() {

                try {
                    socket = new Socket(ip, port);
//                    socket = new Socket("47.90.92.56", 16840);
                    Log.e("JAVA", "建立连接：" + socket);
                    index = 1;
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 发送消息
     */
    public void send(final String deviceid) {
        new Thread() {
            @Override
            public void run() {

                try {
                    // socket.getInputStream()
                    DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
                    String str = "";
//                    str = getDeviceFlag();
                    str = deviceid;
                    writer.writeUTF(str); // 写一个UTF-8的信息
                    FileUtils.writeFile(FileUtils.SOCKET_PATH, "- 发送成功" + ", time:" + App.getCurrentTime() + "\n", true);
                    System.out.println("发送消息：" + str);
                } catch (Exception e) {
                    FileUtils.writeFile(FileUtils.SOCKET_PATH, "- 发送失败" + ", time:" + App.getCurrentTime() + "\n" + "cause-> " + e.getCause() + "\n", true);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @NonNull
    private String getDeviceFlag() {
        String str = null;

        switch (type) {
            case 0:
                str = "device";

                break;
            case 1:
                str = "phone";
                break;
            case 2:
                str = "mi";
                break;
        }
        return str;
    }

    /**
     * 从参数的socket里获取最新的消息
     */
    public void read() {

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (index == 1) {
                        try {
                            // 获取读取流
                            DataInputStream reader = new DataInputStream(socket.getInputStream());
                            while (true) {
                                System.out.println("接收消息");
                                // 读取数据
                                String msg = reader.readUTF();

                                FileUtils.writeFile(FileUtils.SOCKET_PATH, "* 接收成功->" + msg + ", time:" + App.getCurrentTime() + "\n", true);
                                System.out.println("接收到服务器的消息：" + msg);
                                //txt2.setText("受到消息");
                            }
                        } catch (IOException e) {
                            FileUtils.writeFile(FileUtils.SOCKET_PATH, "* 接收失败" + ", time:" + App.getCurrentTime() + "\n" + "cause-> " + e.getCause() + "\n", true);
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 建立服务端连接
     */
    public void disConn() {
        new Thread() {

            @Override
            public void run() {

                try {
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static String getCurrentTime() {
        String str = "";
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        str += mCalendar.get(Calendar.YEAR) + "-";
        str += mCalendar.get(Calendar.MONTH) + 1 + "-";
        str += mCalendar.get(Calendar.DAY_OF_MONTH) + " ";
        str += mCalendar.get(Calendar.HOUR_OF_DAY) + ":";
        str += mCalendar.get(Calendar.MINUTE) + ":";
        str += mCalendar.get(Calendar.SECOND);
        return str;
    }

    public boolean isMao() {
        return type == 0;
    }



    private SimpleClient4IOT simpleClient4IOT;
    String devicename="";
    String phoneNumber="15502113228";
    private String deviceSecret;
    AliIotImpl aliIot;
    private void init(){
        aliIot=new AliIotImpl();
        if(devicename.equals("")){
            aliIot.queryPhoneNumber(phoneNumber, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String json=responseInfo.result;
                    QueryPhoneNumberBean queryPhoneNumberBean= new Gson().fromJson(json,QueryPhoneNumberBean.class);
                    if(null !=queryPhoneNumberBean.gettList() && queryPhoneNumberBean.gettList().size()>0){
                        devicename=queryPhoneNumberBean.gettList().get(0).getPhoneName();
                        phoneNumber=queryPhoneNumberBean.gettList().get(0).getPhoneNumber();
                        aliIot.queryDeviceIsExist(devicename, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String json=responseInfo.result;
                                QueryDeviceIsExistBean queryDeviceIsExistBean=new Gson().fromJson(json,QueryDeviceIsExistBean.class);
                                deviceSecret=queryDeviceIsExistBean.getDeviceInfo().getDeviceSecret();
                                Log.d("LMW","====+++ "+devicename);
                                try {
                                    simpleClient4IOT=new SimpleClient4IOT();
                                    initMQTT2(deviceSecret);
                                    if(null != onCompleteListener){
                                        onCompleteListener.onComplete();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {

                            }
                        });
                    }else{
                        Log.d("LMW","获取对应手机名失败");

                    }
                    Log.d("LMW",phoneNumber+"======"+devicename);

                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    Log.d("LMW","   "+msg);
                }
            });
        }else{
            aliIot.queryDeviceIsExist(devicename, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String json=responseInfo.result;
                    QueryDeviceIsExistBean queryDeviceIsExistBean=new Gson().fromJson(json,QueryDeviceIsExistBean.class);
                    deviceSecret=queryDeviceIsExistBean.getDeviceInfo().getDeviceSecret();
                    Log.d("LMW","====+++ "+devicename);
                    try {
                        simpleClient4IOT=new SimpleClient4IOT();
                        initMQTT2(deviceSecret);
                        if(null != onCompleteListener){
                            onCompleteListener.onComplete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg) {

                }
            });
        }


    }

    public  void initMQTT2(String deviceSecret) throws Exception {
//        String devicename="";
//        if(null== devicename || "".equals(devicename)){
//            return;
//        }

        //客户端设备自己的一个标记，建议是MAC或SN，不能为空，32字符内
//        String clientId = InetAddress.getLocalHost().getHostAddress();
//        String clientId = "test_"+System.currentTimeMillis();
        String clientId = MqttClient.generateClientId();
        ;

        //设备认证
        Map<String, String> params = new HashMap<String, String>();
        params.put("productKey", Const.PRODUCT_KEY); //这个是对应用户在控制台注册的 设备productkey
        params.put("deviceName", devicename); //这个是对应用户在控制台注册的 设备name
        params.put("clientId", clientId);
        String t = System.currentTimeMillis() + "";
        params.put("timestamp", t);

        //MQTT服务器地址，TLS连接使用ssl开头
        String targetServer = "ssl://" + Const.PRODUCT_KEY + ".iot-as-mqtt.cn-shanghai.aliyuncs.com:1883";

        //客户端ID格式，两个||之间的内容为设备端自定义的标记，字符范围[0-9][a-z][A-Z]
        String mqttclientId = clientId + "|securemode=2,signmethod=hmacsha1"
                + ",timestamp=" + t + "|"
                ;


        String mqttUsername =devicename+"&"+Const.PRODUCT_KEY;//mqtt用户名格式
        String mqttPassword = SignUtil.sign(params, deviceSecret, "hmacsha1");//签名
        System.err.println("mqttclientId=" + mqttclientId);

//        SimpleClient4IOT.connectMqtt(targetServer, mqttclientId, mqttUsername, mqttPassword, deviceName);
        simpleClient4IOT.connectMqttAndroid(targetServer, mqttclientId, mqttUsername, mqttPassword, devicename);

    }

    public String getDevicename() {
        return devicename;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDeviceSecret() {
        return deviceSecret;
    }

    public SimpleClient4IOT getSimpleClient4IOT() {
        return simpleClient4IOT;
    }
    public interface OnCompleteListener{
        void onComplete();
    }
    public OnCompleteListener onCompleteListener;
    public void setOnCompleteListener(OnCompleteListener onCompleteListener){
        this.onCompleteListener=onCompleteListener;
    }
}
