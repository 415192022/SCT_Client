package client.socket.li.com.sct_client.bean;

/**
 * Created by MingweiLi on 2018/3/12.
 */

public class QueryPhoneNumberBean extends AliBaseBean<QueryPhoneNumberBean> {
    private String phoneNumber;
    private String phoneName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    @Override
    public String toString() {
        return "QueryPhoneNumberBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", phoneName='" + phoneName + '\'' +
                '}';
    }
}
