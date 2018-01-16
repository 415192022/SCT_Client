package client.socket.li.com.sct_client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView tvShow;
    private TextView tv_device_name;
    private EditText et_ip;
    private EditText et_port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        tvShow = ((TextView) findViewById(R.id.tv_show));
        et_ip = ((EditText) findViewById(R.id.et_ip));
        et_port = ((EditText) findViewById(R.id.et_port));
        tv_device_name= (TextView) findViewById(R.id.tv_device_name);
        tv_device_name.setText("当前设备名称："+App.sApp.random);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PollingUtils.stopUpdataRemaning(this);
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
}
