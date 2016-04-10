package com.example.gabriele.steganography.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriele on 10/04/16.
 */
public class OutputStatsAfterEncoding implements Parcelable {

    private String path;
    private long time;
    private String msg;

    public OutputStatsAfterEncoding(String path, String msg, long time){
        this.path= path;
        this.msg= msg;
        this.time= time;
    }

    public String getPath() {
        return path;
    }

    public long getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }

    protected OutputStatsAfterEncoding(Parcel in) {
        path = in.readString();
        time = in.readLong();
        msg = in.readString();
    }

    public static final Creator<OutputStatsAfterEncoding> CREATOR = new Creator<OutputStatsAfterEncoding>() {
        @Override
        public OutputStatsAfterEncoding createFromParcel(Parcel in) {
            return new OutputStatsAfterEncoding(in);
        }

        @Override
        public OutputStatsAfterEncoding[] newArray(int size) {
            return new OutputStatsAfterEncoding[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeLong(time);
        dest.writeString(msg);
    }
}
