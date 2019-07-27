package com.android.nimdemo.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * TODO
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/07/27 14:43
 */
public class NimAccountInfo implements Parcelable {
    private String accid;
    private String token;
    private String servicecount;

    public NimAccountInfo() {
    }

    protected NimAccountInfo(Parcel in) {
        accid = in.readString();
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accid);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NimAccountInfo> CREATOR = new Creator<NimAccountInfo>() {
        @Override
        public NimAccountInfo createFromParcel(Parcel in) {
            return new NimAccountInfo(in);
        }

        @Override
        public NimAccountInfo[] newArray(int size) {
            return new NimAccountInfo[size];
        }
    };

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServicecount() {
        return servicecount;
    }

    public void setServicecount(String servicecount) {
        this.servicecount = servicecount;
    }
}
