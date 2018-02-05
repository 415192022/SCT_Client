package client.socket.li.com.sct_client.bean;

/**
 * Created by MingweiLi on 2018/1/30.
 */

public class PayloadBean {
    private String lastTime;
    private String time;
    private String productKey;
    private String deviceName;
    private String status;

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "lastTime='" + lastTime + '\'' +
                ", time='" + time + '\'' +
                ", productKey='" + productKey + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
