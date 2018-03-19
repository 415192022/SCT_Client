package client.socket.li.com.sct_client.bean;

/**
 * Created by MingweiLi on 2018/3/13.
 */

public class AllDeviceListByPhoneNumberBean extends AliBaseBean<AllDeviceListByPhoneNumberBean> {

    /**
     * deviceName : uee002
     * phoneName : phone003
     * useTime : 1520925857
     * ueePhoneNumber : 15502113228
     */

    private String deviceName;
    private String phoneName;
    private int useTime;
    private String ueePhoneNumber;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public String getUeePhoneNumber() {
        return ueePhoneNumber;
    }

    public void setUeePhoneNumber(String ueePhoneNumber) {
        this.ueePhoneNumber = ueePhoneNumber;
    }

    @Override
    public String toString() {
        return "AllDeviceListByPhoneNumberBean{" +
                "deviceName='" + deviceName + '\'' +
                ", phoneName='" + phoneName + '\'' +
                ", useTime=" + useTime +
                ", ueePhoneNumber='" + ueePhoneNumber + '\'' +
                '}';
    }
}
