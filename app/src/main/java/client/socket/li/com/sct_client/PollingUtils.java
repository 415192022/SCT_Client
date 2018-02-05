package client.socket.li.com.sct_client;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Ryan Wu on 2017/5/5.
 */

public class PollingUtils {

/*    public static void startUpdataRemaning(Context context, int minutes) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpdateRemindingReceiver.class);
        int requestCode = 0;
        //触发服务的起始时间
        long triggerAtTime = SystemClock.elapsedRealtime();
        PendingIntent pendIntent = PendingIntent.getBroadcast(context,
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP
                , triggerAtTime
                , minutes * 60 * 1000
                , pendIntent);
    }*/

    public static void startUpdataRemaning(Context context, int second) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpdateRemindingReceiver.class);
        int requestCode = 0;
        //触发服务的起始时间
        PendingIntent pendIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        long triggerAtTime = SystemClock.elapsedRealtime();
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, second * 1000, pendIntent);

        long nextTime = System.currentTimeMillis() + second * 1000;
        // 设置闹钟
        // 当前版本为19（4.4）或以上使用精准闹钟
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextTime, pendIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, nextTime, pendIntent);
//            alarmMgr.setWindow(AlarmManager.RTC_WAKEUP, nextTime, 10, pendIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, nextTime, pendIntent);
        }
    }

    public static void stopUpdataRemaning(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpdateRemindingReceiver.class);
        PendingIntent pendIntent = PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 与上面的intent匹配（filterEquals(intent)）的闹钟会被取消
        alarmMgr.cancel(pendIntent);
    }

    public static void startOfflineReconnect(Context context, int second) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, OfflineReconnectReceiver.class);
        int requestCode = 0;
        //触发服务的起始时间
        PendingIntent pendIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        long triggerAtTime = SystemClock.elapsedRealtime();
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, second * 1000, pendIntent);

        long nextTime = System.currentTimeMillis() + second * 1000;
        // 设置闹钟
        // 当前版本为19（4.4）或以上使用精准闹钟
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextTime, pendIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, nextTime, pendIntent);
//            alarmMgr.setWindow(AlarmManager.RTC_WAKEUP, nextTime, 10, pendIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, nextTime, pendIntent);
        }
    }

    public static void stopOfflineReconnect(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, OfflineReconnectReceiver.class);
        PendingIntent pendIntent = PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 与上面的intent匹配（filterEquals(intent)）的闹钟会被取消
        alarmMgr.cancel(pendIntent);
    }

}

