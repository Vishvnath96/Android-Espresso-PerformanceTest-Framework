package com.mmt.travel.app.androidMain.NFR.CPUImpl;

import android.content.Context;
import android.os.Debug;

import com.mmt.travel.app.androidMain.NFR.CPUPojo.CPUManager;
import com.mmt.travel.app.androidMain.NFR.NFRFuntions.Functionality;
import com.mmt.travel.app.androidMain.NFR.NFRFuntions.NFRHelper;
import com.mmt.travel.app.androidMain.NFR.NFRFuntions.adbCommands;
import com.mmt.travel.app.androidMain.NFR.NFRUtils.ApiUrls;
import com.mmt.travel.app.androidMain.NFR.NFRUtils.CreateDBRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Statement;

/**
 * Created by MMT6054 on 25-Jul-17.
 */

public class CPUHelper implements adbCommands {
     CPUManager cpuManager;

    //for collecting CPU stats data
    private  void collectCPUStats(String udid, String methodName, String LOB) throws InterruptedException {
        cpuManager = new CPUManager();
        getTestCaseData(methodName, LOB, cpuManager);
        try {
            if (udid != null) {
                getCpuUsage(cpuManager);
                readThreadCpuTime(cpuManager);

            } else if (udid == null) {
                cpuManager.setUserSpaceCpuUsage(0);
                cpuManager.setSystemCpuUsage(0);
                cpuManager.setIoWaitCpuUsage(0);
                cpuManager.setIrqCpuUsage(0);
                cpuManager.setMainCpuUsage(0);
                cpuManager.setReadThreadCPUTime(0);
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        publish(cpuManager);
    }

    //set method name lob name device name apk version and timestamp for storing in db
    private  CPUManager getTestCaseData(String methodName, String lob, CPUManager cpuManager) {
        cpuManager.setMethodName(methodName);
        cpuManager.setLobName(lob);
        cpuManager.setDeviceId(NFRHelper.getDeviceName());
        cpuManager.setApkVersion(NFRHelper.getAPKNameAndVersion());
        cpuManager.setTimeStamp(NFRHelper.getCurrentTimeStamp());
        return cpuManager;
    }

    //extracting cpu data from running commands as a process
    public  float[] getCpuUsage(CPUManager cpuManager) {
        float[] cpuUsageAsFloat = null;
        String[] cpu_Array;
        try {
            // -m 10, how many entries you want, -d 1, delay by how much, -n 1,
            // number of iterations
            Process p = Runtime.getRuntime().exec("top -d 1 -m 10 -n 1");

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            String line = reader.readLine();
            while (line != null) {
                line = line.toLowerCase();
                if (isCpuStatisticsInfo(line)) {
                    cpuUsageAsFloat = getCpuStatisticsInfo(line);
                    cpuManager.setUserSpaceCpuUsage(cpuUsageAsFloat[0]);
                    cpuManager.setSystemCpuUsage(cpuUsageAsFloat[1]);
                    cpuManager.setIoWaitCpuUsage(cpuUsageAsFloat[2]);
                    cpuManager.setIrqCpuUsage(cpuUsageAsFloat[3]);
                } else if (line.contains("com.makemytrip")) {
                    cpu_Array = line.split("\\s+");
                    Float cpu = Float.parseFloat(cpu_Array[2].replace("%",""));
                    cpuManager.setMainCpuUsage(cpu);
                    break;
                }
                line = reader.readLine();
            }
            p.waitFor();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return cpuUsageAsFloat;
    }

    //get user, system, iqw, irq cpu usage
    private static float[] getCpuStatisticsInfo(String info) {
        info = info.replaceAll(",", "");
        info = info.replaceAll("cpu:", "");
        info = info.replaceAll("user", "");
        info = info.replaceAll("usr", "");
        info = info.replaceAll("system", "");
        info = info.replaceAll("sys", "");
        info = info.replaceAll("iow", "");
        info = info.replaceAll("irq", "");

        info = info.replaceAll("%", "");
        for (int i = 0; i < 5; i++) {
            info = info.replaceAll("  ", " ");
        }
        info = info.trim();
        String[] myString = info.split(" ");
        float[] cpuUsageAsFloat = new float[myString.length];
        for (int i = 0; i < myString.length; i++) {
            myString[i] = myString[i].trim();
            cpuUsageAsFloat[i] = (float) Double.parseDouble(myString[i]);
        }
        return cpuUsageAsFloat;
    }

    private static boolean isCpuStatisticsInfo(String info) {
        if ((info.indexOf("user") != -1 && info.indexOf("system") != -1)
                || (info.indexOf("usr") != -1 && info.indexOf("sys") != -1))
            return true;
        return false;
    }

    //inserting data into qa DB using api service to hit db
    private  void publish(CPUManager cpuManager) {
        CreateDBRequest createDBRequest = new CreateDBRequest();
        createDBRequest.createAPIRequest(cpuManager, ApiUrls.CpuUsageUrl);
    }


    public void perform(Functionality functionality, String[] testParam, Context context) {
        try {
            switch (functionality.toString().toUpperCase()) {
                case "COLLECT":
                    collectCPUStats(testParam[0], testParam[1], testParam[2]);
                    break;
                case "PUBLISH":
                   // publish(statement);
                    break;
                default:
                    System.out.println("Invalid input received");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    //for extracting Time for particular thread
    public static void readThreadCpuTime(CPUManager cpuManager) throws IOException {
        cpuManager.setReadThreadCPUTime(Debug.threadCpuTimeNanos() / 1000000);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Statement statement;
        String testSetParam[] = {NFRHelper.getUDIDOfConnectedDevice(), "testHomePage", "CMN"};
        CPUHelper cpuHelper = new CPUHelper();
        cpuHelper.perform(Functionality.COLLECT, testSetParam, null);
        cpuHelper.publish(null);
    }

}

