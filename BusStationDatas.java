package org.techtown.abcde;

/**
 * Created by YUN on 2017-08-06.
 */


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class BusStationDatas implements Parcelable, Comparable {
    private String busInfo, busStation;

    public BusStationDatas(String busInfo, String busStation) {
        this.busInfo = busInfo;
        this.busStation = busStation;
    }

    public BusStationDatas(Parcel in){
        readFromParcel(in);
    }

    public BusStationDatas(){
    }

    public String getBusInfo() {
        return busInfo;
    }

    public void setBusInfo(String busInfo) {
        this.busInfo = busInfo;
    }

    public String getBusStation() {
        return busStation;
    }

    public void setBusStation(String busStation) {
        this.busStation = busStation;
    }

    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void writeToParcel(Parcel arg0, int arg1) {
        // TODO Auto-generated method stub
        arg0.writeString(busInfo);
        arg0.writeString(busStation);
    }

    private void readFromParcel(Parcel in) {
        // TODO Auto-generated method stub
        busInfo = in.readString();
        busStation = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public BusStationDatas createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new BusStationDatas(source);
        }

        public BusStationDatas[] newArray(int size) {
            // TODO Auto-generated method stub
            return new BusStationDatas[size];
        }
    };

    @Override
    public int compareTo(@NonNull Object o) {
        if(o == null) return 0;

        BusStationDatas bus = (BusStationDatas)o;

        if(Integer.parseInt(this.getBusInfo()) > Integer.parseInt(bus.getBusInfo())) {
            return 1;
        } else if(Integer.parseInt(this.getBusInfo()) < Integer.parseInt(bus.getBusInfo())) {
            return -1;
        } else {
            return 0;
        }
    }
}
