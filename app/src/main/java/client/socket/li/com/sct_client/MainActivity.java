package client.socket.li.com.sct_client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import client.socket.li.com.sct_client.bean.AliIotDeviceInfoBean;
import client.socket.li.com.sct_client.bean.PayloadBean;
import client.socket.li.com.sct_client.util.Const;
import client.socket.li.com.sct_client.util.SimpleClient4IOT;
import client.socket.li.com.sct_client.util.SystemUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView tvShow;
    private TextView tv_state;
    private TextView tv_state1;
    private TextView tv_state2;
    private TextView tv_state3;
    private TextView tv_state_local;
    private TextView tv_device_name;
    private EditText et_ip;
    private EditText et_port;
    private EditText et_input_device_name;
    private Button btn_save_device_name;

    private SimpleClient4IOT simpleClient4IOT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        tvShow = ((TextView) findViewById(R.id.tv_show));
        tv_state = ((TextView) findViewById(R.id.tv_state));
        tv_state1 = ((TextView) findViewById(R.id.tv_state1));
        tv_state2 = ((TextView) findViewById(R.id.tv_state2));
        tv_state3 = ((TextView) findViewById(R.id.tv_state3));
        tv_state_local = ((TextView) findViewById(R.id.tv_state_local));
        et_ip = ((EditText) findViewById(R.id.et_ip));
        et_port = ((EditText) findViewById(R.id.et_port));
        et_input_device_name = ((EditText) findViewById(R.id.et_input_device_name));
        btn_save_device_name = ((Button) findViewById(R.id.btn_save_device_name));
        tv_device_name= (TextView) findViewById(R.id.tv_device_name);
        tv_device_name.setText("当前设备名称："+App.sApp.random);



        et_input_device_name.setText(App.sApp.getLocalDeviceName());
        btn_save_device_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.writeFile(App.sApp.getLocalDeviceNameFile().getAbsolutePath(),et_input_device_name.getText()+"",false);
                startAliIot();
            }
        });


        SystemUtils.init(this);
//        initAliIotSuit();
//        getDeviceState();
        startAliIot();


        simpleClient4IOT=new SimpleClient4IOT();
        simpleClient4IOT.setOnDeviceStateLinstenner(new SimpleClient4IOT.OnDeviceStateLinstenner() {

            @Override
            public void deviceOffLine(Throwable cause, PayloadBean payloadBean) {

                if(null != payloadBean){
                    if(payloadBean.getDeviceName().equals("phone001")){
                        tv_state.setTextColor(0xFFFF0000);
                        tv_state.setText(payloadBean.getDeviceName()+"离线");
                    }
                    if(payloadBean.getDeviceName().equals("uee001")){
                        tv_state1.setTextColor(0xFFFF0000);
                        tv_state1.setText(payloadBean.getDeviceName()+"离线");
                    }
                    if(payloadBean.getDeviceName().equals("uee002")){
                        tv_state2.setTextColor(0xFFFF0000);
                        tv_state2.setText(payloadBean.getDeviceName()+"离线");
                    }
                    if(payloadBean.getDeviceName().equals("uee003")){
                        tv_state3.setTextColor(0xFFFF0000);
                        tv_state3.setText(payloadBean.getDeviceName()+"离线");
                    }
                }

            }

            @Override
            public void onLine(PayloadBean payloadBean) {

                if(null != payloadBean){
                    if(payloadBean.getDeviceName().equals("phone001")){
                        tv_state.setTextColor(0xFF00FF00);
                        tv_state.setText(payloadBean.getDeviceName()+"在线");
                    }
                    if(payloadBean.getDeviceName().equals("uee001")){
                        tv_state1.setTextColor(0xFF00FF00);
                        tv_state1.setText(payloadBean.getDeviceName()+"在线");
                    }
                    if(payloadBean.getDeviceName().equals("uee002")){
                        tv_state2.setTextColor(0xFF00FF00);
                        tv_state2.setText(payloadBean.getDeviceName()+"在线");
                    }
                    if(payloadBean.getDeviceName().equals("uee003")){
                        tv_state3.setTextColor(0xFF00FF00);
                        tv_state3.setText(payloadBean.getDeviceName()+"在线");
                    }

                }

            }

            @Override
            public void localOnline(PayloadBean payloadBean) {
                PollingUtils.stopOfflineReconnect(MainActivity.this);
                if(null != payloadBean){
                    tv_state_local.setTextColor(0xFF00FF00);
                    tv_state_local.setText(payloadBean.getDeviceName()+"本机在线");
                }
            }

            @Override
            public void localOffline() {
                PollingUtils.startOfflineReconnect(MainActivity.this,180);
                tv_state_local.setTextColor(0xFFFF0000);
                tv_state_local.setText("本机离线");
            }

            @Override
            public void deviceConnectionSuccess(IMqttToken asyncActionToken) {
                PollingUtils.stopOfflineReconnect(MainActivity.this);
                tv_state_local.setTextColor(0xFF00FF00);
                tv_state_local.setText("本机在线(连接成功)");
            }

            @Override
            public void deviceConnectionFailure(IMqttToken asyncActionToken, Throwable exception) {
                PollingUtils.startOfflineReconnect(MainActivity.this,180);
                tv_state_local.setTextColor(0xFFFF0000);
                tv_state_local.setText("本机离线(连接失败)");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PollingUtils.stopUpdataRemaning(this);
        PollingUtils.stopOfflineReconnect(MainActivity.this);
    }




    public void onReadFileClick(View view) {
        showTv(false);
    }

    public void onDeleteClick(View view) {
        showTv(true);
    }

    private void showTv(final boolean isDelete) {
        Observable.just("")

                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.e("zan", Thread.currentThread().getName());
                        if (isDelete) {
                            FileUtils.writeFile(FileUtils.SOCKET_PATH, "--", false);
                        }
                        return FileUtils.readFile(new File(FileUtils.SOCKET_PATH));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        tvShow.setText(s);
                    }
                });
    }

    public void onConnClick(View view) {
        String ip=et_ip.getText().toString().trim();
        String port=et_port.getText().toString().trim();
        if("".equals(ip) ||"".equals(port) || null==ip || null==port){
            Toast.makeText(this,"ip或端口号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        App.sApp.conn(ip,Integer.parseInt(port));
        App.sApp.read();

        PollingUtils.startUpdataRemaning(this, 30);
    }

    public void onDisConnClick(View view) {
        App.sApp.disConn();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("注意")
                        .setMessage("确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                finish();
                                System.exit(0);

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                            }
                        })
                        .show();
                break;

            default:
                break;
        }
        return false;
    }






    public  void initMQTT2() throws Exception {
        String devicename=et_input_device_name.getText()+"";
        String deviceSecret="";
        if(null== devicename || "".equals(devicename)){
            return;
        }

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

        if("uee001".equals(devicename)){
            deviceSecret=Const.DEVICE_SECRET1;
        }
        if("uee002".equals(devicename)){
            deviceSecret=Const.DEVICE_SECRET2;
        }
        if("uee003".equals(devicename)){
            deviceSecret=Const.DEVICE_SECRET4;
        }
        if("phone001".equals(devicename)){
            deviceSecret=Const.PHONE_SECRET1;
        }
        String mqttPassword = SignUtil.sign(params, deviceSecret, "hmacsha1");//签名
        System.err.println("mqttclientId=" + mqttclientId);

//        SimpleClient4IOT.connectMqtt(targetServer, mqttclientId, mqttUsername, mqttPassword, deviceName);
        simpleClient4IOT.connectMqttAndroid(targetServer, mqttclientId, mqttUsername, mqttPassword, devicename);

    }
    //用于测试的topic
//    private static String pubTopic = "/" + Const.PRODUCT_KEY  + "/" + Const.DEVICE_NAME2 + "/regeister";
//    private static String pubTopic1 = "/" + Const.PRODUCT_KEY  + "/" + Const.DEVICE_NAME2 + "/regeister";
    public void getDeviceState(String devicename){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, "http://192.168.100.67:7410/alidevicestate/"+devicename, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                Log.d("LMW","查询设备成功 "+json);
                AliIotDeviceInfoBean aliIotDeviceInfoBean=new Gson().fromJson(json,AliIotDeviceInfoBean.class);
                String status=aliIotDeviceInfoBean.getDeviceStatusList().get(0).getStatus();
                String deviceName=aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName();
                if("ONLINE".equals(status)){
                    if(deviceName.equals("phone001")){
                        tv_state.setTextColor(0xFF00FF00);
                        tv_state.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"在线");
                    }
                    if(deviceName.equals("uee001")){
                        tv_state1.setTextColor(0xFF00FF00);
                        tv_state1.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"在线");
                    }
                    if(deviceName.equals("uee002")){
                        tv_state2.setTextColor(0xFF00FF00);
                        tv_state2.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"在线");
                    }
                    if(deviceName.equals("uee003")){
                        tv_state3.setTextColor(0xFF00FF00);
                        tv_state3.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"在线");
                    }

                }else{
                    if(deviceName.equals("phone001")){
                        tv_state.setTextColor(0xFFFF0000);
                        tv_state.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"离线");
                    }
                    if(deviceName.equals("uee001")){
                        tv_state1.setTextColor(0xFFFF0000);
                        tv_state1.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"离线");
                    }
                    if(deviceName.equals("uee002")){
                        tv_state2.setTextColor(0xFFFF0000);
                        tv_state2.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"离线");
                    }
                    if(deviceName.equals("uee003")){
                        tv_state3.setTextColor(0xFFFF0000);
                        tv_state3.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"离线");
                    }

                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.d("LMW","查询设备失败 "+msg);
            }
        });
    }

    private void startAliIot(){
        Observable.just(null)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        try {
                            initMQTT2();
//                            getDeviceState("uee001");
//                            getDeviceState("uee002");
//                            getDeviceState("uee003");
                            getDeviceState("phone001");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

}
