package com.example.laba6;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.yandex.mapkit.geometry.Point;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sight implements Parcelable {
    private String name;
    private double x;
    private double y;
    private String description;
    private String time0 = "-";
    private String time1 = "-";

    Sight(String name, String description, double x, double y) {
        this.name = name;
        this.description = description;
        this.x = x;
        this.y = y;
    }

    Sight(String name, String description, double x, double y, String time) throws ParseException {
        this.name = name;
        this.description = description;
        this.x = x;
        this.y = y;
        String[] times = time.split("-");
        this.time0 = times[0];
        this.time1 = times[1];
    }

    protected Sight(Parcel in) {
        name = in.readString();
        x = in.readDouble();
        y = in.readDouble();
        description = in.readString();
        time0 = in.readString();
        time1 = in.readString();
    }

    public static final Creator<Sight> CREATOR = new Creator<Sight>() {
        @Override
        public Sight createFromParcel(Parcel in) {
            return new Sight(in);
        }

        @Override
        public Sight[] newArray(int size) {
            return new Sight[size];
        }
    };

    public String getTime() {
        if (time0.equals("-"))
            return "-";
        return time0 + "-" + time1;
    }

    public String getRealTime0() {
        if (time0.equals("-"))
            return "00:00";
        return time0;
    }

    public String getRealTime1() {
        if (time1.equals("-"))
            return "23:59";
        return time1;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(x);
        parcel.writeDouble(y);
        parcel.writeString(description);
        parcel.writeString(time0);
        parcel.writeString(time1);
    }
}