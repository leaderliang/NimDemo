package com.android.nimdemo.net;

/**
 * TODO
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/07/27 15:10
 */
public class NimUser {

    /**
     * userId : 577431757259800578
     * accid : 577431757259800578
     * userType : 4
     * channel : PC
     */

    private String userId;
    private String accid;
    private int userType;
    private String channel;

    public NimUser(String userId, String accid, int userType, String channel) {
        this.userId = userId;
        this.accid = accid;
        this.userType = userType;
        this.channel = channel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
