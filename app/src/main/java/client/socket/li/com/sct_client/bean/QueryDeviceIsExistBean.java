package client.socket.li.com.sct_client.bean;

/**
 * Created by MingweiLi on 2018/3/12.
 */

public class QueryDeviceIsExistBean  {

    private String requestId;
    private boolean success;
    private DeviceInfoBean deviceInfo;

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

    public DeviceInfoBean getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoBean deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public static class DeviceInfoBean {

        private String deviceId;
        private String deviceSecret;
        private String productKey;
        private String deviceStatus;
        private String deviceName;
        private String gmtCreate;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceSecret() {
            return deviceSecret;
        }

        public void setDeviceSecret(String deviceSecret) {
            this.deviceSecret = deviceSecret;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }

        public String getDeviceStatus() {
            return deviceStatus;
        }

        public void setDeviceStatus(String deviceStatus) {
            this.deviceStatus = deviceStatus;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        @Override
        public String toString() {
            return "DeviceInfoBean{" +
                    "deviceId='" + deviceId + '\'' +
                    ", deviceSecret='" + deviceSecret + '\'' +
                    ", productKey='" + productKey + '\'' +
                    ", deviceStatus='" + deviceStatus + '\'' +
                    ", deviceName='" + deviceName + '\'' +
                    ", gmtCreate='" + gmtCreate + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QueryDeviceIsExistBean{" +
                "requestId='" + requestId + '\'' +
                ", success=" + success +
                ", deviceInfo=" + deviceInfo +
                '}';
    }
}
