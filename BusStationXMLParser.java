package org.techtown.abcde;

/**
 * Created by YUN on 2017-08-06.
 */

import android.os.Handler;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;


public class BusStationXMLParser extends XMLParser implements Runnable{
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
        if(parser == null){
            mDataList = null;
            Log.d("BusStationXMLParser", "Paser Object is null");
        }else{
            mDataList = new ArrayList<BusStationDatas>();
            String busStationInfo = null, busStationName = null;
            String tag;
            try{
                int parserEvent = parser.getEventType();
                int tagIdentifier = 0;

                while(parserEvent != XmlPullParser.END_DOCUMENT){
                    switch(parserEvent){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                                                break;
                        case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            if(tag.equals("arrtime")){
                                tagIdentifier = 1;
                            }else if(tag.equals("routeno")){
                                tagIdentifier = 2;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            if(tagIdentifier == 1){
                                busStationInfo = parser.getText().trim();
                            }else if(tagIdentifier == 2){
                                busStationName = parser.getText().trim();
                                BusStationDatas data = new BusStationDatas(busStationInfo, busStationName);
                                mDataList.add(data);
                            }
                            tagIdentifier = 0;
                            break;
                    }
                    parserEvent = parser.next();
                }
            }catch(Exception e){
                Log.d("BusStationXMLParser", e.getMessage());
            }
        }
        Log.d("BusStationXMLResult", Integer.toString(mDataList.size()));
    }

    public ArrayList<BusStationDatas> getResult(){
        return mDataList;
    }

    public void run() {
        // TODO Auto-generated method stub
        startParsing();
        mHandler.sendEmptyMessage(0);
    }
}