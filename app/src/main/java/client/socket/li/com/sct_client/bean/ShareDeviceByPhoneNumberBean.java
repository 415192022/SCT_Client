package client.socket.li.com.sct_client.bean;

/**
 * Created by MingweiLi on 2018/3/13.
 */

public class ShareDeviceByPhoneNumberBean extends AliBaseBean<ShareDeviceByPhoneNumberBean> {

    /**
     * ueePhoneNumber : 15502113227
     * shareDevice : uee002
     * sharePhone : phone002
     * masterPhoneName : phone003
     * useTime : 1520925935
     */

    private String ueePhoneNumber;
    private String shareDevice;
    private String sharePhone;
    private String masterPhoneName;
    private int useTime;

    public String getUeePhoneNumber() {
        return ueePhoneNumber;
    }

    public void setUeePhoneNumber(String ueePhoneNumber) {
        this.ueePhoneNumber = ueePhoneNumber;
    }

    public String getShareDevice() {
        return shareDevice;
    }

    public void setShareDevice(String shareDevice) {
        this.shareDevice = shareDevice;
    }

    public String getSharePhone() {
        return sharePhone;
    }

    public void setSharePhone(String sharePhone) {
        this.sharePhone = sharePhone;
    }

    public String getMasterPhoneName() {
        return masterPhoneName;
    }

    public void setMasterPhoneName(String masterPhoneName) {
        this.masterPhoneName = masterPhoneName;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    @Override
    public String toString() {
        return "ShareDeviceByPhoneNumberBean{" +
                "ueePhoneNumber='" + ueePhoneNumber + '\'' +
                ", shareDevice='" + shareDevice + '\'' +
                ", sharePhone='" + sharePhone + '\'' +
                ", masterPhoneName='" + masterPhoneName + '\'' +
                ", useTime=" + useTime +
                '}';
    }
}
