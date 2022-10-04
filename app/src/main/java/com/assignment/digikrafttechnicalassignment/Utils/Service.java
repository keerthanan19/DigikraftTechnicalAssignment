package com.assignment.digikrafttechnicalassignment.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.assignment.digikrafttechnicalassignment.MainActivity;
import com.assignment.digikrafttechnicalassignment.NetWork.HandleApiResponse;
import com.assignment.digikrafttechnicalassignment.Object.Data;

import java.text.CollationElementIterator;
import java.util.List;

public class Service {

    private static int progressStatus;

    public static void getAllData(Activity activity){
        HandleApiResponse handleApiResponse = new HandleApiResponse(activity,"http://www.poznan.pl/mim/plan/");
        handleApiResponse.getAllData( new HandleApiResponse.CallBackDataDelegate() {
            @Override
            public void onResponseSuccess(List<Data> dataList) {
                Log.e("getAllChild", "result "+dataList.size());
                int count = 0;
                activity.startActivity(new Intent(activity,MainActivity.class));
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    public static void progressBar( int status, Handler handler, ProgressBar progressBar, TextView textView) {
       progressStatus = status ;
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 20;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;

        if(dist != 0){
            return (dist);
        }else {
            return (0);
        }

    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
