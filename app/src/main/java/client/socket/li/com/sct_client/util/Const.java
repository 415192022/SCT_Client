package client.socket.li.com.sct_client.util;

/**
 * Created by MingweiLi on 2018/1/26.
 */

public class Const {

    public static final String PRODUCT_KEY="Ft9glS0MPmE";
//    public static final String HOST_NAME="http://192.168.100.67:7410";
    public static final String HOST_NAME="http://lmingwei.free.ngrok.cc";
    /**
     *   send a message by devicename  get
     *   /alisendmessage/:devicename/:content
     *
     */
    public static final String SEND_MESSAGE=HOST_NAME+"/alisendmessage/";
    /**
     *  obtain device state
     *  /alidevicestate/:devicename
     */
    public static final String OBTAIN_DEVICE_STATE=HOST_NAME+"/alidevicestate/";
    /**
     *  在Ali后台注册设备
     *  /registerDevice/:newDeviceName
     */
    public static final String REGISTER_DEVICE=HOST_NAME+"/registerDevice/";
    /**
     *  在Ali后台注册手机设备，根据手机号、手机名建立一对一关系
     *  /registerPhone/:phoneNumber/:phoneName
     */
    public static final String REGISTER_PHONE=HOST_NAME+"/registerPhone/";
    /**
     *  查询手机、设备是否存在，以及deviceSecret
     *  /queryDeviceIsExist/:deviceName
     */
    public static final String QUERY_DEVICE_IS_EXIST=HOST_NAME+"/queryDeviceIsExist/";
    /**
     *  根据设备名和手机名建立绑定关系
     *  /bindDevice/:deviceName/:phoneName
     */
    public static final String BIND_DEVICE=HOST_NAME+"/bindDevice/";
    /**
     *  解绑设备
     *  unbindDevice/:deviceName
     */
    public static final String UNBIND_DEICE=HOST_NAME+"/unbindDevice/";
    /**
     *  根据手机号查询所有绑定的设备
     *  /queryAllDeviceByPhoneNumber/:phoneNumber
     */
    public static final String QUERY_ALL_DEVICE_BY_PHONE_NUMBER=HOST_NAME+"/queryAllDeviceByPhoneNumber/";
    /**
     *  根据设备名称查询该设备是否被绑定
     *  /deviceIsBound/:deviceName
     */
    public static final String DEVICE_IS_BOUND=HOST_NAME+"/deviceIsBound/";
    /**
     *  通过手机名查询所绑定的手机号
     *  /queryPhoneNumber/:phoneName
     */
    public static final String QUERY_PHONE_NUMBER=HOST_NAME+"/queryPhoneNumber/";
    /**
     *  分享设备
     *  /sharePhone/:masterPhone/:sharePhone/:deviceName
     */
    public static final String SHARE_PHONE=HOST_NAME+"/sharePhone/";
    /**
     *   取消分享
     *   /cancelShareDevice/:masterPhone/:sharePhone/:deviceName
     */
    public static final String CANCEL_SHARE_DEVICE=HOST_NAME+"/cancelShareDevice/";
    /**
     *  查询当前登录用户有没有被分享的设备
     *  queryShareDeviceByPhoneNumber/:phoneNumber
     */
    public static final String QUERY_SHARE_DEVICE_BY_PHONE_NUMBER=HOST_NAME+"/queryShareDeviceByPhoneNumber/";
    /**
     *  主控手机通过设备名查询该设备分享给哪些手机
     *  queryDeviceShareToPhone/:masterPhoneName/:deviceName
     */
    public static final String QUERY_DEVICE_SHARE_TO_PHONE=HOST_NAME+"/queryDeviceShareToPhone/";

}
