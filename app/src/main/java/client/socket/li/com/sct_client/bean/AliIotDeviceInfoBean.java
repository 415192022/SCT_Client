package client.socket.li.com.sct_client.bean;

import java.util.List;

/**
 * Created by uee on 2018/1/31.
 */
public class AliIotDeviceInfoBean {


    private String requestId;
    private boolean success;
    private List<DeviceStatusListBean> deviceStatusList;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DeviceStatusListBean> getDeviceStatusList() {
        return deviceStatusList;
    }

    public void setDeviceStatusList(List<DeviceStatusListBean> deviceStatusList) {
        this.deviceStatusList = deviceStatusList;
    }

    @Override
    public String toString() {
        return "AliIotDeviceInfoBean{" +
                "requestId='" + requestId + '\'' +
                ", success=" + success +
                ", deviceStatusList=" + deviceStatusList +
                '}';
    }

    public static class DeviceStatusListBean {

        private String deviceId;
        private String deviceName;
        private String lastOnlineTime;
        private String status;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getLastOnlineTime() {
            return lastOnlineTime;
        }

        public void setLastOnlineTime(String lastOnlineTime) {
            this.lastOnlineTime = lastOnlineTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "DeviceStatusListBean{" +
                    "deviceId='" + deviceId + '\'' +
                    ", deviceName='" + deviceName + '\'' +
                    ", lastOnlineTime='" + lastOnlineTime + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
