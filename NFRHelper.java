package com.mmt.travel.app.androidMain.NFR.NFRFuntions;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;

import com.makemytrip.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;


/**
 * Created by MMT6054 on 09-Jul-17.
 */

public class NFRHelper implements adbCommands {
    public static String getCurrentTimeStamp() {
        Date date = new Date();
        return String.valueOf(new Timestamp(date.getTime()));
    }
    //method to get ApkName And Version
    public static String getAPKNameAndVersion() {
        String versionName = BuildConfig.VERSION_NAME.replace(".","");
        //versionName.replace(".","");
        return versionName;
    }
    //method to get Device Name
    public static String getDeviceName(String serialNumber) throws IOException {
        Process process = Runtime.getRuntime().exec(String.format(adbCommands.deviceModelCommand, serialNumber));
        //InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = null;
        if ((line = br.readLine()) != null) {
            return line;
        }

        return null;
    }

    //method to get Device Name
    public static String getDeviceName(){
        String deviceName = Build.MODEL;
        return deviceName;
    }


    //method to get UDID of connected device
    public static String getUDIDOfConnectedDevice() throws IOException, InterruptedException {
        String deviceUDID = null;
        String line = null;
        Process process = Runtime.getRuntime().exec(String.format(adbCommands.getUdidCommands));
        process.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = buf.readLine()) != null) {
            if (line.contains("List of devices") && !line.isEmpty()) {
                line = buf.readLine();
                if (line.contains("unauthorized")) {
                   deviceUDID = line.replaceAll("unauthorized", "").trim();
                } else
                    deviceUDID = line.replaceAll("device", "").trim();
            }
        }

        return deviceUDID;
    }

    //method to get device id
    public static String getDeviceUDID(Context context){
        String android_id = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
        return android_id;
    }


}
