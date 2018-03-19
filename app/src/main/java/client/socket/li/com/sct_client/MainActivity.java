package client.socket.li.com.sct_client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Base64;
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
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import client.socket.li.com.sct_client.bean.AliIotDeviceInfoBean;
import client.socket.li.com.sct_client.bean.AllDeviceListByPhoneNumberBean;
import client.socket.li.com.sct_client.bean.DeviceListBean;
import client.socket.li.com.sct_client.bean.PayloadBean;
import client.socket.li.com.sct_client.bean.QueryDeviceStateParmBean;
import client.socket.li.com.sct_client.bean.ShareDeviceByPhoneNumberBean;
import client.socket.li.com.sct_client.model.AliIotImpl;
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
    private SwipeRefreshLayout srl_root;

    private Button btn_send_message;
    private Button btn_device_state;
    private Button btn_register_device;
    private Button btn_register_phone;
    private Button btn_device_is_exist;
    private Button btn_bind_device;
    private Button btn_unbind_device;
    private Button btn_bind_device_list;
    private Button btn_is_bind;
    private Button btn_id_query_phone_number;
    private Button btn_share_device;
    private Button btn_cancel_share;
    private Button btn_query_share_device_list;
    private Button btn_query_share_device_phone_list;

    private RecyclerView rv_device_list;

    AliIotImpl aliIot;
    private SimpleClient4IOT simpleClient4IOT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        App.sApp.setOnCompleteListener(new App.OnCompleteListener() {
            @Override
            public void onComplete() {
                setListener();
            }
        });

        rv_device_list= (RecyclerView) findViewById(R.id.rv_device_list);
        rv_device_list.setHasFixedSize(true);
        rv_device_list.setLayoutManager(new LinearLayoutManager(this));
        rv_device_list.addItemDecoration(new MyDecoration());
        aliIot=new AliIotImpl();
        deviceListRecyclerViewAdpter=new DeviceListRecyclerViewAdpter(MainActivity.this);
        rv_device_list.setAdapter(deviceListRecyclerViewAdpter);
        getDeviceList();


        tvShow = ((TextView) findViewById(R.id.tv_show));
        tv_state = ((TextView) findViewById(R.id.tv_state));
        tv_state1 = ((TextView) findViewById(R.id.tv_state1));
        tv_state2 = ((TextView) findViewById(R.id.tv_state2));
        tv_state3 = ((TextView) findViewById(R.id.tv_state3));
        tv_state_local = ((TextView) findViewById(R.id.tv_state_local));



        btn_send_message = ((Button) findViewById(R.id.btn_send_message));
        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json="{\"isShareMessage\":true,\"sharedDeviceName\":\"uee001\"}";
                String json64= new String(Base64.encode(json.getBytes(), Base64.DEFAULT));
                aliIot.sendMessage("phone002", json64, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_device_state = ((Button) findViewById(R.id.btn_device_state));
        btn_device_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.obtainDeviceState("uee002", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_register_device = ((Button) findViewById(R.id.btn_register_device));
        btn_register_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.registerDevice("device001", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_register_phone = ((Button) findViewById(R.id.btn_register_phone));
        btn_register_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.registerPhone("15502113231", "phone010", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_device_is_exist = ((Button) findViewById(R.id.btn_device_is_exist));
        btn_device_is_exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.queryDeviceIsExist("device004", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_bind_device = ((Button) findViewById(R.id.btn_bind_device));
        btn_bind_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AliIotImpl aliIot=new AliIotImpl();
                final EditText editText = new EditText(MainActivity.this);
                editText.setHint("请输入要绑定的设备名称");
                android.support.v7.app.AlertDialog.Builder inputDialog =
                        new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                inputDialog.setTitle("绑定设备").setView(editText);
                inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aliIot.bindDevice(editText.getText()+"", App.sApp.getDevicename(), new RequestCallBack<String>() {
                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        Toast.makeText(MainActivity.this,
                                                responseInfo.result,
                                                Toast.LENGTH_SHORT).show();
                                        getDeviceList();
                                    }

                                    @Override
                                    public void onFailure(HttpException error, String msg) {
                                        Log.d("LMW",msg);
                                        Toast.makeText(MainActivity.this,
                                                msg,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).show();










            }
        });
        btn_unbind_device = ((Button) findViewById(R.id.btn_unbind_device));
        btn_unbind_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.unBindDevice("device001", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_bind_device_list = ((Button) findViewById(R.id.btn_bind_device_list));
        btn_bind_device_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.queryAllDeviceByPhoneNumber("15502113227", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_is_bind = ((Button) findViewById(R.id.btn_is_bind));
        btn_is_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.deviceIsBound("15502113227", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_id_query_phone_number = ((Button) findViewById(R.id.btn_id_query_phone_number));
        btn_id_query_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.queryPhoneNumber("uee002", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_share_device = ((Button) findViewById(R.id.btn_share_device));
        btn_share_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.sharePhone("15502113227", "15502113231", "uee002", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_cancel_share = ((Button) findViewById(R.id.btn_cancel_share));
        btn_cancel_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.cancelShareDevice("15502113227", "15502113231", "uee002", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_query_share_device_list = ((Button) findViewById(R.id.btn_query_share_device_list));
        btn_query_share_device_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.queryShareDeviceByPhoneNumber("15502113227", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });
        btn_query_share_device_phone_list = ((Button) findViewById(R.id.btn_query_share_device_phone_list));
        btn_query_share_device_phone_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliIot.queryDeviceShareToPhone("15502113227", "uee002", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("LMW",responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("LMW",msg);
                    }
                });
            }
        });


        et_ip = ((EditText) findViewById(R.id.et_ip));
        et_port = ((EditText) findViewById(R.id.et_port));
        et_input_device_name = ((EditText) findViewById(R.id.et_input_device_name));
        btn_save_device_name = ((Button) findViewById(R.id.btn_save_device_name));
        tv_device_name= (TextView) findViewById(R.id.tv_device_name);
        srl_root= (SwipeRefreshLayout) findViewById(R.id.srl_root);
        srl_root.setColorSchemeColors(
                getResources().getColor(R.color.Color_DarkTurquoise)
                ,getResources().getColor(R.color.Color_DeepSkyBlue)
                ,getResources().getColor(R.color.Color_DodgerBlue)
        );
        srl_root.setProgressViewOffset(true, 50, 200);
//        srl_root.setEnabled(true);
        srl_root.setProgressBackgroundColor(R.color.Color_White);
//        srl_root.setRefreshing(true);
        srl_root.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDeviceList();
            }
        });

        tv_device_name.setText("当前设备名称："+App.sApp.random);

        tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS  );
                startActivity(intent);
            }
        });



        et_input_device_name.setText(App.sApp.getLocalDeviceName());
        btn_save_device_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.writeFile(App.sApp.getLocalDeviceNameFile().getAbsolutePath(),et_input_device_name.getText()+"",false);
//                startAliIot();
            }
        });


        SystemUtils.init(this);

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






    //用于测试的topic
//    private static String pubTopic = "/" + Const.PRODUCT_KEY  + "/" + Const.DEVICE_NAME2 + "/regeister";
//    private static String pubTopic1 = "/" + Const.PRODUCT_KEY  + "/" + Const.DEVICE_NAME2 + "/regeister";
    public void getDeviceState(String devicename){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(1);
        httpUtils.send(HttpRequest.HttpMethod.GET, "http://192.168.100.67:7410/alidevicestate/"+devicename, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                Log.d("LMW","查询设备成功 "+json);
                AliIotDeviceInfoBean aliIotDeviceInfoBean=new Gson().fromJson(json,AliIotDeviceInfoBean.class);
                String status=aliIotDeviceInfoBean.getDeviceStatusList().get(0).getStatus();
                String deviceName=aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName();
                if("ONLINE".equals(status)){
                    if(deviceName.equals(App.sApp.getDevicename())){
                        tv_state.setTextColor(0xFF00FF00);
                        tv_state.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"在线");
                    }

                }else{
                    if(deviceName.equals(App.sApp.getDevicename())){
                        tv_state.setTextColor(0xFFFF0000);
                        tv_state.setText(aliIotDeviceInfoBean.getDeviceStatusList().get(0).getDeviceName()+"离线");
                    }

                }

                deviceListRecyclerViewAdpter.addData(aliIotDeviceInfoBean,deviceListBeanList);
                srl_root.setRefreshing(false);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.d("LMW","查询设备失败 "+msg);
                srl_root.setRefreshing(false);
            }
        });
    }



    public void setListener(){
        simpleClient4IOT=App.sApp.getSimpleClient4IOT();
        simpleClient4IOT.setOnDeviceStateLinstenner(new SimpleClient4IOT.OnDeviceStateLinstenner() {
            @Override
            public void deviceOffLine(Throwable cause, PayloadBean payloadBean) {
//                getDeviceState(App.sApp.getDevicename());
                if(null != payloadBean){
                    if(payloadBean.getDeviceName().equals(App.sApp.getDevicename())){
                        tv_state.setTextColor(0xFFFF0000);
                        tv_state.setText(payloadBean.getDeviceName()+"离线");
                    }
                }
                deviceListRecyclerViewAdpter.setDeviceStateBeanList(payloadBean);
            }

            @Override
            public void onLine(PayloadBean payloadBean) {

                if(null != payloadBean){
                    if(payloadBean.getDeviceName().equals(App.sApp.getDevicename())){
                        tv_state.setTextColor(0xFF00FF00);
                        tv_state.setText(payloadBean.getDeviceName()+"在线");

                    }
                    deviceListRecyclerViewAdpter.setDeviceStateBeanList(payloadBean);
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

    List<DeviceListBean> deviceListBeanList=new ArrayList<>();
    DeviceListRecyclerViewAdpter deviceListRecyclerViewAdpter;
    public void getDeviceList(){
        deviceListBeanList.clear();
        Log.d("LMW",App.sApp.getPhoneNumber()+"==========");
        aliIot.queryAllDeviceByPhoneNumber(App.sApp.getPhoneNumber(), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                Log.d("LMW",json);
                AllDeviceListByPhoneNumberBean allDeviceListByPhoneNumberBean=new Gson().fromJson(json,AllDeviceListByPhoneNumberBean.class);
                for(AllDeviceListByPhoneNumberBean a:allDeviceListByPhoneNumberBean.gettList()){
                    DeviceListBean deviceListBean=new DeviceListBean();
                    deviceListBean.setDeviceName(a.getDeviceName());
                    deviceListBean.setShared(false);
                    deviceListBeanList.add(deviceListBean);
                }
                getShareDeviceList();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.d("LMW",msg);
                srl_root.setRefreshing(false);
            }
        });


    }

    public void getShareDeviceList(){
        aliIot.queryShareDeviceByPhoneNumber(App.sApp.getPhoneNumber(), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                Log.d("LMW",json);
                ShareDeviceByPhoneNumberBean shareDeviceByPhoneNumberBean=new Gson().fromJson(json,ShareDeviceByPhoneNumberBean.class);
                for(ShareDeviceByPhoneNumberBean s:shareDeviceByPhoneNumberBean.gettList()){
                    Log.d("LMW",s.getShareDevice());
                    DeviceListBean deviceListBean=new DeviceListBean();
                    deviceListBean.setDeviceName(s.getShareDevice());
                    deviceListBean.setShared(true);
                    deviceListBean.setMasterPhoneName(s.getMasterPhoneName());
                    deviceListBeanList.add(deviceListBean);
                }


                Observable
                        .from(deviceListBeanList)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Action1<DeviceListBean>() {
                            @Override
                            public void call(DeviceListBean deviceListBean) {
                                try {
                                    Thread.sleep(100);
                                    getDeviceState(deviceListBean.getDeviceName());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });


                srl_root.setRefreshing(false);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.d("LMW",msg);
                srl_root.setRefreshing(false);
            }
        });
    }

}
