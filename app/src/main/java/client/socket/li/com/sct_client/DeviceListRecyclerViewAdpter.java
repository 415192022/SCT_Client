package client.socket.li.com.sct_client;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

import client.socket.li.com.sct_client.bean.AliIotDeviceInfoBean;
import client.socket.li.com.sct_client.bean.DeviceListBean;
import client.socket.li.com.sct_client.bean.PayloadBean;
import client.socket.li.com.sct_client.bean.ShareMessageBean;
import client.socket.li.com.sct_client.model.AliIotImpl;

/**
 * Created by MingweiLi on 2018/3/13.
 */

public class DeviceListRecyclerViewAdpter extends RecyclerView.Adapter<DeviceListRecyclerViewAdpter.MyViewHolder> {
    public Context context;
    private AliIotDeviceInfoBean aliIotDeviceInfoBeen=null;
    private List<DeviceListBean> deviceStateBeanList=new ArrayList<>();
    private PayloadBean payloadBean;
    public DeviceListRecyclerViewAdpter(Context context ){
        this.context=context;
    }
    public void addData(AliIotDeviceInfoBean aliIotDeviceInfoBean ,List<DeviceListBean> deviceStateBeanList){
        this.deviceStateBeanList=deviceStateBeanList;
        this.aliIotDeviceInfoBeen=aliIotDeviceInfoBean;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_device_list,parent,false);
        return new MyViewHolder(view);
    }

    public List<DeviceListBean> getDeviceStateBeanList() {
        return deviceStateBeanList;
    }

    public void setDeviceStateBeanList(PayloadBean payloadBean) {
        this.payloadBean = payloadBean;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(holder.tv_deviceName.equals("")){
            holder.tv_deviceState.setTextColor(Color.GRAY);
            holder.tv_deviceState.setText("连接中...");
        }
        if(null != deviceStateBeanList){
            if(deviceStateBeanList.get(position).getShared()){
                holder.tv_deviceName.setTextColor(Color.GRAY);
                holder.tv_deviceName.setText(deviceStateBeanList.get(position).getDeviceName()+"(被分享的设备)");
            }else{
                holder.tv_deviceName.setTextColor(Color.BLACK);
                holder.tv_deviceName.setText(deviceStateBeanList.get(position).getDeviceName());
            }
        }
        if(aliIotDeviceInfoBeen.getDeviceStatusList().get(0).getDeviceName().equals(deviceStateBeanList.get(position).getDeviceName())){
            if(aliIotDeviceInfoBeen.getDeviceStatusList().get(0).getStatus().equals("ONLINE")){
                holder.tv_deviceState.setTextColor(Color.GREEN);
                holder.tv_deviceState.setText("在线");
            }else{
                holder.tv_deviceState.setTextColor(Color.RED);
                holder.tv_deviceState.setText("离线");
            }
        }

        if(null!=deviceStateBeanList && null!= payloadBean && deviceStateBeanList.get(position).getDeviceName().equals(payloadBean.getDeviceName())){
            if(payloadBean.getStatus().equals("online")){
                holder.tv_deviceState.setTextColor(Color.GREEN);
                holder.tv_deviceState.setText("在线");
            }else{
                holder.tv_deviceState.setTextColor(Color.RED);
                holder.tv_deviceState.setText("离线");
            }
        }

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AliIotImpl aliIot=new AliIotImpl();
                if(deviceStateBeanList.get(position).getShared()){
                    Log.d("LMW","取消分享");
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("删除设备");
                    builder.setMessage("您确定要删除分享设备"+deviceStateBeanList.get(position).getDeviceName());
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            aliIot.cancelShareDevice(deviceStateBeanList.get(position).getMasterPhoneName(), App.sApp.getDevicename(), deviceStateBeanList.get(position).getDeviceName(), new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    Toast.makeText(context,responseInfo.result,Toast.LENGTH_SHORT).show();
                                    ((MainActivity)context).getDeviceList();
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    Log.d("LMW","解除绑定");
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("解除绑定");
                    builder.setMessage("您确定要删除设备"+deviceStateBeanList.get(position).getDeviceName());
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            aliIot.unBindDevice(deviceStateBeanList.get(position).getDeviceName(), new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    Toast.makeText(context,responseInfo.result,Toast.LENGTH_SHORT).show();
                                    ((MainActivity)context).getDeviceList();
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


        holder.ll_root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!deviceStateBeanList.get(position).getShared()){
                    final AliIotImpl aliIot=new AliIotImpl();
                    final EditText editText = new EditText(context);
                    editText.setHint("请输入要分享的手机号");
                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(context);
                    inputDialog.setTitle("分享设备"+deviceStateBeanList.get(position).getDeviceName()).setView(editText);
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
                                    aliIot.sharePhone(App.sApp.getDevicename(), editText.getText() + "", deviceStateBeanList.get(position).getDeviceName(), new RequestCallBack<String>() {
                                        @Override
                                        public void onSuccess(ResponseInfo<String> responseInfo) {
                                            Toast.makeText(context,
                                                    responseInfo.result,
                                                    Toast.LENGTH_SHORT).show();
                                            ShareMessageBean shareMessageBean=new ShareMessageBean();
                                            shareMessageBean.setShareMessage(true);
                                            shareMessageBean.setSharedDeviceName(deviceStateBeanList.get(position).getDeviceName());
                                            String json=new Gson().toJson(shareMessageBean);
                                            aliIot.sendMessage("phone002", json, new RequestCallBack<String>() {
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

                                        @Override
                                        public void onFailure(HttpException error, String msg) {
                                            Toast.makeText(context,
                                                    msg,
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).show();
                }
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return deviceStateBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_deviceName;
        private TextView tv_deviceState;
        private LinearLayout ll_root;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_deviceName= (TextView) itemView.findViewById(R.id.tv_deviceName);
            tv_deviceState= (TextView) itemView.findViewById(R.id.tv_deviceState);
            ll_root= (LinearLayout) itemView.findViewById(R.id.ll_root);
        }
    }
}
