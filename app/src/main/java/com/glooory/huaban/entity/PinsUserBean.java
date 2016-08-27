package com.glooory.huaban.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class PinsUserBean implements Parcelable {
    //Pins 中的 user
    /* "user":{
        "user_id":17368129,
            "username":"hbk112",
            "urlname":"gain1123",
            "created_at":1432860604,
            "avatar":{
                "id":92107218,
                "farm":"farm1",
                "bucket":"hbimg",
                "key":"4591dc495d509736f69517c9480af27b7f7103072d931-QJWISP",
                "type":"image/jpeg",
                "height":"1500",
                "frames":"1",
                "width":"1000"
    },
        "extra":null
    }  */

    private int user_id;
    private String username;
    private String urlname;
    private int created_at;
    private String avatar;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.user_id);
        dest.writeString(this.username);
        dest.writeString(this.urlname);
        dest.writeInt(this.created_at);
        dest.writeString(this.avatar);
    }

    public PinsUserBean() {
    }

    protected PinsUserBean(Parcel in) {
        this.user_id = in.readInt();
        this.username = in.readString();
        this.urlname = in.readString();
        this.created_at = in.readInt();
        this.avatar = in.readString();
    }

    public static final Parcelable.Creator<PinsUserBean> CREATOR = new Parcelable.Creator<PinsUserBean>() {
        @Override
        public PinsUserBean createFromParcel(Parcel source) {
            return new PinsUserBean(source);
        }

        @Override
        public PinsUserBean[] newArray(int size) {
            return new PinsUserBean[size];
        }
    };
}
