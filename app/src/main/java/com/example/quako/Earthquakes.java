package com.example.quako;

import java.util.Date;

public class Earthquakes {
    private final String mPlace;
    private final String mOffset;
    private final double mMagnitude;
    private final Date mDate;
    private final String mUrl;

    public Earthquakes(String offset, String place, double magnitude, Date date, String url) {
        mPlace = place;
        mOffset = offset;
        mMagnitude = magnitude;
        mDate = date;
        mUrl = url;
    }

    public String getPlace() {
        return mPlace;
    }

    public String getOffset() {
        return mOffset;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public Date getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
