package client.socket.li.com.sct_client.bean;

/**
 * Created by MingweiLi on 2018/3/16.
 */

public class SendMessageBean {
    private String deviceName;
    private String content;

    @Override
    public String toString() {
        return "SendMessageBean{" +
                "deviceName='" + deviceName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getContent() {
        return content;
    }
}
