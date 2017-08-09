package edu.handong.org.myapplication;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by user on 2017-08-07.
 */

public class BusStationDatas implements Parcelable {
    String stationID;
    String busStationLati;
    String busStationLongi;

    public BusStationDatas(String stationID, String busStationLati, String busStationLongi) {
        this.stationID = stationID;
        this.busStationLati = busStationLati;
        this.busStationLongi = busStationLongi;
    }

    public BusStationDatas(Parcel in){
        readFromParcel(in);
    }

    public BusStationDatas(){}

    public String getBusID() {
        return stationID;
    }

    public void setBusID(String stationID) {
        this.stationID = stationID;
    }

    public String getBusStationLati() {
        return busStationLati;
    }

    public void setBusStationLati(String busStationLati) {
        this.stationID = busStationLati;
    }

    public String getBusStationLongi() {
        return busStationLongi;
    }

    public void setBusStationLongi(String busStatiionLongi) {this.busStationLongi = busStatiionLongi;}

    //----------------------------------------------------------------------------------------------

    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void writeToParcel(Parcel arg0, int arg1) {
        // TODO Auto-generated method stub
        arg0.writeString(stationID);
        arg0.writeString(busStationLati);
        arg0.writeString(busStationLongi);
    }

    private void readFromParcel(Parcel in) {
        // TODO Auto-generated method stub
        stationID = in.readString();
        busStationLati = in.readString();
        busStationLongi = in.readString();

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
}