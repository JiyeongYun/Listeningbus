package edu.handong.org.myapplication;

import android.os.Handler;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by user on 2017-08-07.
 */

public class BusStationXMLParser extends XMLParser implements Runnable {
    private ArrayList<BusStationDatas> mDataList;
    private Handler mHandler;

    public BusStationXMLParser(String addr, Handler handler) {
        super(addr);
        // TODO Auto-generated constructor stub
        mHandler = handler;
    }

    public void startParsing() {
        // TODO Auto-generated method stub
        XmlPullParser parser = getXMLParser("utf-8");

        if (parser == null) {
            mDataList = null;
            Log.d("BusStationXMLParser", "Paser Object is null");
        } else {
            mDataList = new ArrayList<BusStationDatas>();
            String busStationID = null, busStationLati = null, busStationLongi = null;
            String tag;
            try {
                int parserEvent = parser.getEventType();
                int tagIdentifier = 0;

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            if (tag.equals("nodeid")) {
                                tagIdentifier = 1;
                            } else if (tag.equals("gpslati")) {
                                tagIdentifier = 2;
                            } else if (tag.equals("gpslong")) {
                                tagIdentifier = 3;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            if (tagIdentifier == 1) {
                                busStationID = parser.getText().trim();
                            } else if (tagIdentifier == 2) {
                                busStationLati = parser.getText().trim();
                            } else if (tagIdentifier == 3) {
                                busStationLongi = parser.getText().trim();
                                BusStationDatas data = new BusStationDatas(busStationID, busStationLati, busStationLongi);
                                mDataList.add(data);
                            }
                            tagIdentifier = 0;
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                Log.d("BusStationXMLParser", e.getMessage());
            }
        }

        Log.d("BusStationXMLResult", Integer.toString(mDataList.size()));
    }

    public ArrayList<BusStationDatas> getResult() {
        return mDataList;
    }

    public void run() {
        // TODO Auto-generated method stub
        startParsing();
        mHandler.sendEmptyMessage(0);
    }

}
