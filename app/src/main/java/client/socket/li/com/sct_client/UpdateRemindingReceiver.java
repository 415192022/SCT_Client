package client.socket.li.com.sct_client;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Mingwei Li on 2017/11/2
 * 自动检查更新
 */
@SuppressLint("LongLogTag")
public class UpdateRemindingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ZAN", "收到广播");
        PollingUtils.startUpdataRemaning(context, App.SECOND_SOCKET);
        FileUtils.writeFile(FileUtils.SOCKET_PATH, "-------------------------------------------\n-> 广播--" + ", time:" + App.getCurrentTime() + "\n", true);
//        ping();
        
        socket(context);
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

    private void socket(Context context) {
        App.sApp.send("device-"+App.sApp.random);
    }
}
