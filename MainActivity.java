package org.techtown.abcde;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private long mStartTime, mEndTime;
    private BusStationXMLParser mXMLParser;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                mStartTime = System.currentTimeMillis();
                mXMLParser = new BusStationXMLParser("http://openapi.tago.go.kr/openapi/service/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList?ServiceKey=Utk4pE8vGB%2BaPGsKKwhKQreMwU2cR4K9Dxc7t%2BjJEdgetcgm3E1UGZhdeYDxTSzaydt5a8SvvDdEhNNDbDr7HA%3D%3D&cityCode=37010&nodeId=PHB351008001", mHandler);
                Thread thread = new Thread(mXMLParser);
                thread.start();
            }
        });
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            mEndTime = System.currentTimeMillis();
            Log.d("Taken Time", Long.toString((mEndTime - mStartTime) / 1000L));

            ArrayList<BusStationDatas> dataList = mXMLParser.getResult();
            int dataListSize = dataList.size();
            Log.d("Data List Size", Integer.toString(dataListSize));

            ArrayList<BusStationDatas> buses = new ArrayList<>();

            for(BusStationDatas bus : dataList) {
                if(bus.getBusStation().equals("108")) {
                    buses.add(bus);
                }
            }

            BusStationDatas nearBus;

            Collections.sort(buses);
            nearBus = buses.get(0);
            nearBus.getBusStation();
            nearBus.getBusInfo();


            for (int i = 0; i < dataListSize; i++) {
                int time;
                time=Integer.parseInt(dataList.get(i).getBusInfo());

                Log.d("XML Parsing Result", dataList.get(i).getBusStation() + "번 버스가 " + time/60 + "분 후 도착합니다." );
            }
        }
    };
}
