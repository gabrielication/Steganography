package com.example.gabriele.steganography.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabriele on 10/04/16.
 */
public class OutputStats implements Parcelable {

    private String path;
    private long time;
    private String msg;

    public OutputStats(String path, String msg, long time){
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

    protected OutputStats(Parcel in) {
        path = in.readString();
        time = in.readLong();
        msg = in.readString();
    }

    public static final Creator<OutputStats> CREATOR = new Creator<OutputStats>() {
        @Override
        public OutputStats createFromParcel(Parcel in) {
            return new OutputStats(in);
        }

        @Override
        public OutputStats[] newArray(int size) {
            return new OutputStats[size];
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

/**
 * This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */