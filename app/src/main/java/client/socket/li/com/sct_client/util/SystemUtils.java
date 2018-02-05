package client.socket.li.com.sct_client.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.PowerManager;

import java.util.List;

/**
 * Created by MingweiLi on 2017/5/10.
 */

public class SystemUtils {
    private SystemUtils(){};
    private static SystemUtils systemUtils;
    private static Context context;
    public static void init(Context c){
        context=c;
        if(null == systemUtils){
//            synchronized (SystemUtils.class){
//                if(null == systemUtils){
            systemUtils=new SystemUtils();
//                }
//            }
        }
    }
    public static SystemUtils getInstance(){
        return systemUtils;
    }

    private PowerManager.WakeLock mWakelock;

    /**
     * 由黑屏唤醒屏幕
     */
    public void wakeUpScreen(){
//        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
//        //解锁
//        kl.disableKeyguard();
//        //获取电源管理器对象
//        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
//        //点亮屏幕
//        wl.acquire();
//        //释放
//        wl.release();
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
        mWakelock.acquire();
        mWakelock.release();
        mWakelock=null;
        acquireWakeLock();
    }


    /**
     * 亮屏状态下持续唤醒，延迟睡眠
     * @param on
     */
    public  void keepScreenOn(boolean on) {
        if (on) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                    |PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "==KeepScreenOn==");
            mWakelock.acquire();
            mWakelock.release();
            mWakelock = null;
        } else {
            if (mWakelock != null) {
                mWakelock.release();
                mWakelock = null;
            }
        }
//        acquireWakeLock();
    }


    /**
     * 判断屏幕是否亮起
     * @return
     */
    public boolean screenIsLight(){
        PowerManager powerManager = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        return powerManager.isScreenOn();
    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(200);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }




    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    private void acquireWakeLock()
    {
        if (null == mWakelock)
        {
            PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
            mWakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, "PostLocationService");
            if (null != mWakelock)
            {
                mWakelock.acquire();
            }
        }
        releaseWakeLock();
    }

    //释放设备电源锁
    public void releaseWakeLock()
    {
        if (null != mWakelock)
        {
            mWakelock.release();
            mWakelock = null;
        }
    }


    //不亮背光
    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    public void wakeUpOfkeep() {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                |PowerManager.SCREEN_BRIGHT_WAKE_LOCK , "==KeepScreenOn==");
        mWakelock.acquire();
    }


}
