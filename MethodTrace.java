package com.mmt.travel.app.androidMain.NFR.CPUImpl;

import android.os.Debug;

import static com.mmt.travel.app.androidMain.utilities.StringHelper.uniqueString;

/**
 * Created by MMT6054 on 26-Jul-17.
 */

public class MethodTrace {

    //for getting method trace data
    public static void getTrace(String activityName) {
        try {
            Debug.startMethodTracing(activityName, 6 * 1024 * 1024);
            System.out.println("method tracing started ....");
            Thread.sleep(4000);
            stopTrace();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        stopTrace();
    }

    //for stopping method trace data
    public static void stopTrace() {
        try {
            Debug.stopMethodTracing();
            System.out.println("method tracing end");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}
