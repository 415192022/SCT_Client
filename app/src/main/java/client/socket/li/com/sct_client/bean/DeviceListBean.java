package client.socket.li.com.sct_client.bean;

/**
 * Created by MingweiLi on 2018/3/13.
 */

public class DeviceListBean {
    private String deviceName;
    private Boolean isShared;
    private Boolean isOnline;
    private String masterPhoneName;

    public String getMasterPhoneName() {
        return masterPhoneName;
    }

    public void setMasterPhoneName(String masterPhoneName) {
        this.masterPhoneName = masterPhoneName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Boolean getShared() {
        return isShared;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    @Override
    public String toString() {
        return "DeviceListBean{" +
                "deviceName='" + deviceName + '\'' +
                ", isShared=" + isShared +
                ", isOnline=" + isOnline +
                ", masterPhoneName=" + masterPhoneName +
                '}';
    }
}
