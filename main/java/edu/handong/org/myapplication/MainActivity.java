package edu.handong.org.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private BusStationXMLParser mXMLParser;
    public double latitude;
    public double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startLocationService();
                // TODO Auto-generated method stub
                mXMLParser = new BusStationXMLParser("http://openapi.tago.go.kr/openapi/service/BusSttnInfoInqireService/getCrdntPrxmtSttnList?ServiceKey=Utk4pE8vGB%2BaPGsKKwhKQreMwU2cR4K9Dxc7t%2BjJEdgetcgm3E1UGZhdeYDxTSzaydt5a8SvvDdEhNNDbDr7HA%3D%3D&gpsLati=36.039589&gpsLong=129.366269&numOfRows=999&pageSize=999&pageNo=1&startPage=1", mHandler);
                Thread thread = new Thread(mXMLParser);
                thread.start();
            }
        });
    }

    public void startLocationService() {
        long minTime = 10000;
        float minDistance = 0;
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 25);
        }

        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                    }

                    @Override
                    public void onProviderEnabled(String s) {
                    }

                    @Override
                    public void onProviderDisabled(String s) {
                    }
                });
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            ArrayList<BusStationDatas> dataList = mXMLParser.getResult();
            int dataListSize = dataList.size();
            double[] store = new double[dataListSize];


            for (int i = 0; i < dataListSize; i++) {
                double a = Double.parseDouble(dataList.get(i).getBusStationLati());
                double b = Double.parseDouble(dataList.get(i).getBusStationLongi());
                double dist = distance(a, b, latitude, longitude);
                store[i] = dist;
            }

            double min = Double.MAX_VALUE;
            int minIndex = -1;

            for (int i = 0; i < store.length; i++) {
                if(store[i] < min) {
                    min = store[i];
                    minIndex = i;
                }
            }

            String perfect_busID = dataList.get(minIndex).getBusID();


            //출력하는 부분
            Log.d("Data List Size", Integer.toString(dataListSize));
            for (int i = 0; i < dataListSize; i++) {
                if(i==0)
                    Log.d("현재 위치의 위도,경도", "위도(Lati) : " + dataList.get(i).getBusStationLati() + " " + ">> 경도(Longi) : " + dataList.get(i).getBusStationLongi());
                else
                    Log.d("버스 정류장의 위도,경도", i + "번 째의 버스정류장ID : " + dataList.get(i).getBusID()  + " >> 위도(Lati) : " + dataList.get(i).getBusStationLati() + " " + ">> 경도(Longi) : " + dataList.get(i).getBusStationLongi());
            }

            for(int i = 0; i<store.length; i++){
                Log.d("거리계산 ", i + "번 째 정류장과 현재 위치와의 거리는 " + String.valueOf(store[i]));
            }
            Log.d("결과", "가장 가까운 정류장은 " + minIndex + "번 째 정류장입니다. 버스정류장의 ID는 \"" + perfect_busID + "\" 입니다.");
        }
    };

    public double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = rad2deg(Math.acos(dist)) * 1609.344;
        return dist;
    }

    private double deg2rad(double deg) { return (deg * Math.PI / 180.0); }
    private double rad2deg(double rad) { return (rad * 180 / Math.PI); }

}
