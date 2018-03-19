package client.socket.li.com.sct_client.model;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import client.socket.li.com.sct_client.util.Const;

/**
 * Created by MingweiLi on 2018/3/9.
 */

public class AliIotImpl implements IAliIot{
    HttpUtils httpUtils=new HttpUtils();

    public AliIotImpl(){
        httpUtils.configCurrentHttpCacheExpiry(1);
    }

    /**
     *   send a message by devicename  get
     *   /alisendmessage/:devicename/:content
     *
     */
    @Override
    public void sendMessage(String devicename, String conten, RequestCallBack<String> requestCallBack) {
        String url=Const.SEND_MESSAGE
//                +devicename+"/"+conten
                ;
        Log.d("LMW",url);
        url.trim();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("devicename",devicename);
        requestParams.addBodyParameter("content",conten);
        httpUtils.send(HttpRequest.HttpMethod.POST, url,requestParams, requestCallBack);
    }

    /**
     *  obtain device state
     *  /alidevicestate/:devicename
     */
    @Override
    public void obtainDeviceState(String deviceName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET, Const.OBTAIN_DEVICE_STATE+deviceName,requestCallBack);

    }

    /**
     *  在Ali后台注册设备
     *  /registerDevice/:newDeviceName
     */
    @Override
    public void registerDevice(String newDeviceName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET, Const.REGISTER_DEVICE+newDeviceName, requestCallBack);
    }

    /**
     *  在Ali后台注册手机设备，根据手机号、手机名建立一对一关系
     *  /registerPhone/:phoneNumber/:phoneName
     */
    @Override
    public void registerPhone(String phoneNumber, String phoneName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.REGISTER_PHONE+phoneNumber+"/"+phoneName,requestCallBack);
    }

    /**
     *  查询手机、设备是否存在，以及deviceSecret
     *  /queryDeviceIsExist/:deviceName
     */
    @Override
    public void queryDeviceIsExist(String deviceName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.QUERY_DEVICE_IS_EXIST+deviceName,requestCallBack);
    }

    /**
     *  根据设备名和手机名建立绑定关系
     *  /bindDevice/:deviceName/:phoneName
     */
    @Override
    public void bindDevice(String deviceName, String phoneNme, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.BIND_DEVICE+deviceName+"/"+phoneNme,requestCallBack);
    }

    /**
     *  解绑设备
     *  unbindDevice/:deviceName
     */
    @Override
    public void unBindDevice(String deviceName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.UNBIND_DEICE+deviceName,requestCallBack);
    }

    /**
     *  根据手机号查询所有绑定的设备
     *  /queryAllDeviceByPhoneNumber/:phoneNumber
     */
    @Override
    public void queryAllDeviceByPhoneNumber(String phoneNumber, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.QUERY_ALL_DEVICE_BY_PHONE_NUMBER+phoneNumber,requestCallBack);
    }

    /**
     *  根据设备名称查询该设备是否被绑定
     *  /deviceIsBound/:deviceName
     */
    @Override
    public void deviceIsBound(String deviceName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.DEVICE_IS_BOUND+deviceName,requestCallBack);
    }

    /**
     *  通过手机名查询所绑定的手机号
     *  /queryPhoneNumber/:phoneName
     */
    @Override
    public void queryPhoneNumber(String phoneName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.QUERY_PHONE_NUMBER+phoneName,requestCallBack);
    }

    /**
     *  分享设备
     *  /sharePhone/:masterPhone/:sharePhone/:deviceName
     */
    @Override
    public void sharePhone(String masterPhone, String sharePhone, String deviceName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.SHARE_PHONE+masterPhone+"/"+sharePhone+"/"+deviceName,requestCallBack);
    }

    /**
     *   取消分享
     *   /cancelShareDevice/:masterPhone/:sharePhone/:deviceName
     */
    @Override
    public void cancelShareDevice(String masterPhone, String sharePhone, String deviceName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.CANCEL_SHARE_DEVICE+masterPhone+"/"+sharePhone+"/"+deviceName,requestCallBack);
    }

    /**
     *  查询当前登录用户有没有被分享的设备
     *  queryShareDeviceByPhoneNumber/:phoneNumber
     */
    @Override
    public void queryShareDeviceByPhoneNumber(String phoneNumber, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.QUERY_SHARE_DEVICE_BY_PHONE_NUMBER+"/"+phoneNumber,requestCallBack);
    }

    /**
     *  主控手机通过设备名查询该设备分享给哪些手机
     *  queryDeviceShareToPhone/:masterPhoneName/:deviceName
     */
    @Override
    public void queryDeviceShareToPhone(String masterPhoneName, String deviceName, RequestCallBack<String> requestCallBack) {
        httpUtils.send(HttpRequest.HttpMethod.GET,Const.QUERY_DEVICE_SHARE_TO_PHONE+masterPhoneName+"/"+deviceName,requestCallBack);
    }

}
