package client.socket.li.com.sct_client.bean;

/**
 * Created by MingweiLi on 2018/3/16.
 */

public class ShareMessageBean {
    public Boolean isShareMessage;
    public String sharedDeviceName;

    public String getSharedDeviceName() {
        return sharedDeviceName;
    }

    public void setSharedDeviceName(String sharedDeviceName) {
        this.sharedDeviceName = sharedDeviceName;
    }

    public Boolean getShareMessage() {
        return isShareMessage;
    }

    public void setShareMessage(Boolean shareMessage) {
        isShareMessage = shareMessage;
    }

    @Override
    public String toString() {
        return "ShareMessageBean{" +
                "isShareMessage=" + isShareMessage +
                ", sharedDeviceName='" + sharedDeviceName + '\'' +
                '}';
    }
}
