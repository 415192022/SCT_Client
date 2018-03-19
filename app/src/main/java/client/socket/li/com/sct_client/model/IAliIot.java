package client.socket.li.com.sct_client.model;

import com.lidroid.xutils.http.callback.RequestCallBack;

import client.socket.li.com.sct_client.bean.AliBaseBean;

/**
 * Created by MingweiLi on 2018/3/9.
 */

public interface IAliIot {
    /**
     *   send a message by devicename  get
     *   /alisendmessage/:devicename/:content
     *
     */
    void sendMessage(String devicename, String conten, RequestCallBack<String> requestCallBack);
    /**
     *  obtain device state
     *  /alidevicestate/:devicename
     */
    void obtainDeviceState(String deviceName, RequestCallBack<String> requestCallBack);
    /**
     *  在Ali后台注册设备
     *  /registerDevice/:newDeviceName
     */
    void registerDevice(String newDeviceName,RequestCallBack<String> requestCallBack);
    /**
     *  在Ali后台注册手机设备，根据手机号、手机名建立一对一关系
     *  /registerPhone/:phoneNumber/:phoneName
     */
    void registerPhone(String phoneNumber,String phoneName,RequestCallBack<String> requestCallBack);
    /**
     *  查询手机、设备是否存在，以及deviceSecret
     *  /queryDeviceIsExist/:deviceName
     */
    void queryDeviceIsExist(String deviceName,RequestCallBack<String> requestCallBack);
    /**
     *  根据设备名和手机名建立绑定关系
     *  /bindDevice/:deviceName/:phoneName
     */
    void bindDevice(String deviceName,String phoneNme,RequestCallBack<String> requestCallBack);
    /**
     *  解绑设备
     *  unbindDevice/:deviceName
     */
    void unBindDevice(String deviceName,RequestCallBack<String> requestCallBack);
    /**
     *  根据手机号查询所有绑定的设备
     *  /queryAllDeviceByPhoneNumber/:phoneNumber
     */
    void queryAllDeviceByPhoneNumber(String phoneNumber,RequestCallBack<String> requestCallBack);
    /**
     *  根据设备名称查询该设备是否被绑定
     *  /deviceIsBound/:deviceName
     */
    void deviceIsBound(String deviceName,RequestCallBack<String> requestCallBack);
    /**
     *  通过手机名查询所绑定的手机号
     *  /queryPhoneNumber/:phoneName
     */
    void queryPhoneNumber(String phoneName,RequestCallBack<String> requestCallBack);
    /**
     *  分享设备
     *  /sharePhone/:masterPhone/:sharePhone/:deviceName
     */
    void sharePhone(String masterPhone,String sharePhone,String deviceName,RequestCallBack<String> requestCallBack);
    /**
     *   取消分享
     *   /cancelShareDevice/:masterPhone/:sharePhone/:deviceName
     */
    void cancelShareDevice(String masterPhone,String sharePhone,String deviceName,RequestCallBack<String> requestCallBack);
    /**
     *  查询当前登录用户有没有被分享的设备
     *  queryShareDeviceByPhoneNumber/:phoneNumber
     */
    void queryShareDeviceByPhoneNumber(String phoneNumber,RequestCallBack<String> requestCallBack);
    /**
     *  主控手机通过设备名查询该设备分享给哪些手机
     *  queryDeviceShareToPhone/:masterPhoneName/:deviceName
     */
    void queryDeviceShareToPhone(String masterPhoneName,String deviceName,RequestCallBack<String> requestCallBack);

}
