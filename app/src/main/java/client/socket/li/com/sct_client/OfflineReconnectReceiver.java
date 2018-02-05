package client.socket.li.com.sct_client;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.eclipse.paho.client.mqttv3.MqttClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import client.socket.li.com.sct_client.App;
import client.socket.li.com.sct_client.FileUtils;
import client.socket.li.com.sct_client.PollingUtils;
import client.socket.li.com.sct_client.bean.AliIotDeviceInfoBean;
import client.socket.li.com.sct_client.util.Const;
import client.socket.li.com.sct_client.util.SystemUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Mingwei Li on 2017/11/2
 */
@SuppressLint("LongLogTag")
public class OfflineReconnectReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ZAN", "收到广播");
        PollingUtils.startOfflineReconnect(context,180);
//        SystemUtils.getInstance().wakeUpOfkeep();
//        SystemUtils.getInstance().releaseWakeLock();
        reassociate(context);
    }

    private void ping() {
//        Observable.just("")
//
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s1) {
//                        Log.e("zan 1UpdateRemindingReceiver- ", Thread.currentThread().getName());
//                        String[] commandArray = {"sh", "-c", "ping -q -i 0.2 -c 20 www.baidu.com"};
//                        final String[] info = new String[1];
//                        CommandUtils.execCommand(commandArray).subscribe(s -> info[0] = s, Throwable::printStackTrace);
//                        return info[0];
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.e("zan 2UpdateRemindingReceiver- ", Thread.currentThread().getName());
//                        FileUtils.writeFile(FileUtils.PING_PATH, s, true);
//                    }
//                });
    }

//    private void socket(Context context) {
//        App.sApp.send("device-"+App.sApp.random);
//    }
public void reassociate(Context context){
    final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    wifiManager.reassociate();
    Observable.timer(60000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED){
                        getDeviceState(App.sApp.getLocalDeviceName());
                    }

                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {}
            });
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
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.d("LMW","查询设备失败 "+msg);
            }
        });
    }

}
