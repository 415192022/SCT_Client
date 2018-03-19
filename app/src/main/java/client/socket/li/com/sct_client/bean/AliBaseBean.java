package client.socket.li.com.sct_client.bean;

import java.util.List;

/**
 * Created by uee on 2018/2/7.
 */
public class AliBaseBean<T>{
    private Boolean isSuccess;
    private String message;
    private List<T> tList;

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void settList(List<T> tList) {
        this.tList = tList;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<T> gettList() {
        return tList;
    }

    @Override
    public String toString() {
        return "AliBaseBean{" +
                "isSuccess=" + isSuccess +
                ", message='" + message + '\'' +
                ", tList=" + tList +
                '}';
    }
}
