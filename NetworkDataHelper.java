package com.mmt.travel.app.androidMain.NFR.NetworkDataImpl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;

import com.mmt.travel.app.androidMain.NFR.NFRFuntions.Functionality;
import com.mmt.travel.app.androidMain.NFR.NFRFuntions.NFRHelper;
import com.mmt.travel.app.androidMain.NFR.NFRUtils.ApiUrls;
import com.mmt.travel.app.androidMain.NFR.NFRUtils.CreateDBRequest;
import com.mmt.travel.app.androidMain.NFR.NetworkDataPojo.NetworkDataManager;

import java.io.IOException;
import java.sql.Statement;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by MMT6054 on 28-Jul-17.
 */

public class NetworkDataHelper {

    NetworkDataManager networkDataManager;
    private static int uid;

    //for collecting network data usage
    public void collectNetworkStats(String udid, String methodName, String LOB, Context context) throws IOException {
        networkDataManager = new NetworkDataManager();
        getTestCaseData(methodName, LOB, networkDataManager);

        if (udid != null) {
            uid = android.os.Process.myUid();
            getNetworkStatsUid(networkDataManager, uid, context);
        } else if (udid == null) {
            networkDataManager.setMobileRxData(0);
            networkDataManager.setMobileTxData(0);
            networkDataManager.setMobileTotalData(0);
            networkDataManager.setWifiRxData(0);
            networkDataManager.setWifiTxData(0);
            networkDataManager.setWifiTotalData(0);
            networkDataManager.setNetwork_Carrier_Name(null);
            networkDataManager.setNetworkType(null);
        }
        publish(networkDataManager);
    }

    //inserting data into qa DB using api service to hit db
    private void publish(NetworkDataManager networkDataManager) {
        CreateDBRequest createDBRequest = new CreateDBRequest();
        createDBRequest.createAPIRequest(networkDataManager, ApiUrls.NetworkUsageUrl);
    }

    //set method name lob name device name apk version and timestamp for storing in db
    private NetworkDataManager getTestCaseData(String methodName, String lob, NetworkDataManager networkDataManager) {
        networkDataManager.setMethodName(methodName);
        networkDataManager.setLobName(lob);
        networkDataManager.setDeviceId(NFRHelper.getDeviceName());
        networkDataManager.setApkVersion(NFRHelper.getAPKNameAndVersion());
        networkDataManager.setTimeStamp(NFRHelper.getCurrentTimeStamp());
        return networkDataManager;
    }

    //capturing data usage for RX and TX ,first identifying network type and then extract data as per used network
    public void getNetworkStatsUid(NetworkDataManager networkDataManager, int uid, Context context) {
        NetworkInfo networkInfo = getNetworkType(context);
        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            networkDataManager.setMobileTxData(TrafficStats.getUidTxBytes(uid));
            networkDataManager.setMobileRxData(TrafficStats.getUidRxBytes(uid));
            networkDataManager.setMobileTotalData(TrafficStats.getUidTxBytes(uid) + TrafficStats.getUidRxBytes(uid));
            networkDataManager.setWifiTxData(0);
            networkDataManager.setWifiRxData(0);
            networkDataManager.setWifiTotalData(0);
            networkDataManager.setNetwork_Carrier_Name(networkInfo.getExtraInfo());
            networkDataManager.setNetworkType(networkInfo.getSubtypeName());
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            networkDataManager.setMobileTxData(0);
            networkDataManager.setMobileRxData(0);
            networkDataManager.setMobileTotalData(0);
            networkDataManager.setWifiTxData(TrafficStats.getUidTxBytes(uid));
            networkDataManager.setWifiRxData(TrafficStats.getUidRxBytes(uid));
            networkDataManager.setWifiTotalData(TrafficStats.getUidTxBytes(uid) + TrafficStats.getUidRxBytes(uid));
            networkDataManager.setNetwork_Carrier_Name(networkInfo.getExtraInfo());
            networkDataManager.setNetworkType("WiFi");
        }

    }

    //for getting what type of network in use
    private NetworkInfo getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();

    }


    public void perform(Functionality functionality, String[] statusArray, Context context) {
        try {
            switch (functionality.toString().toUpperCase()) {
                case "COLLECT":
                    try {
                        collectNetworkStats(statusArray[0], statusArray[1], statusArray[2], context);
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case "PUBLISH":
                    //publish(statement);
                    break;
                default:
                    System.out.println("Invalid input received...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


}
