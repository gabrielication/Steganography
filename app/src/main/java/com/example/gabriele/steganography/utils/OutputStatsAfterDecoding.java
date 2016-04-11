package com.example.gabriele.steganography.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriele on 11/04/16.
 */
public class OutputStatsAfterDecoding implements Parcelable {

    private String decodedmsg;

    private long time;

    public OutputStatsAfterDecoding(String decodedmsg, long time) {
        this.decodedmsg = decodedmsg;
        this.time = time;
    }

    public String getDecodedmsg() {
        return decodedmsg;
    }

    public long getTime() {
        return time;
    }

    protected OutputStatsAfterDecoding(Parcel in) {
        decodedmsg = in.readString();
        time = in.readLong();
    }

    public static final Creator<OutputStatsAfterDecoding> CREATOR = new Creator<OutputStatsAfterDecoding>() {
        @Override
        public OutputStatsAfterDecoding createFromParcel(Parcel in) {
            return new OutputStatsAfterDecoding(in);
        }

        @Override
        public OutputStatsAfterDecoding[] newArray(int size) {
            return new OutputStatsAfterDecoding[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(decodedmsg);
        dest.writeLong(time);
    }
}
