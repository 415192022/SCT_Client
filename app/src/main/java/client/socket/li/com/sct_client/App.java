package client.socket.li.com.sct_client;

import android.app.Application;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Zan on 2017/12/18 0018.
 * App.
 */

public class App extends Application {
    public static App sApp;
    int index = 0;
    int random=new Random().nextInt(100000);

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
        sApp = this;
        Log.e("zan - > ", SOCKET_PATH);
        if (isMao()) {
            SECOND_SOCKET = 30;
        } else {
            SECOND_SOCKET = 30;
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
}
