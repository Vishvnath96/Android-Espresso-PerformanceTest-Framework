package com.mmt.travel.app.androidMain.NFR.CPUPojo;

import java.math.BigDecimal;

/**
 * Created by MMT6054 on 25-Jul-17.
 */

public class CPUManager {
    //private fields
    private String methodName;
    private String deviceId;
    private String apkVersion;
    private String lobName;
    private String timeStamp;
    private float userSpaceCpuUsage;
    private float systemCpuUsage;
    private float ioWaitCpuUsage;
    private float irqCpuUsage;
    private float mainCpuUsage;
    private double readThreadCPUTime;

    //getters and setters
    public float getUserSpaceCpuUsage() {
        return userSpaceCpuUsage;
    }

    public void setUserSpaceCpuUsage(float userSpaceCpuUsage) {
        this.userSpaceCpuUsage = userSpaceCpuUsage;
    }

    public float getSystemCpuUsage() {
        return systemCpuUsage;
    }

    public void setSystemCpuUsage(float systemCpuUsage) {
        this.systemCpuUsage = systemCpuUsage;
    }

    public float getIoWaitCpuUsage() {
        return ioWaitCpuUsage;
    }

    public void setIoWaitCpuUsage(float ioWaitCpuUsage) {
        this.ioWaitCpuUsage = ioWaitCpuUsage;
    }

    public float getIrqCpuUsage() {
        return irqCpuUsage;
    }

    public void setIrqCpuUsage(float irqCpuUsage) {
        this.irqCpuUsage = irqCpuUsage;
    }

    public float getMainCpuUsage() {
        return mainCpuUsage;
    }

    public void setMainCpuUsage(float mainCpuUsage) {
        this.mainCpuUsage = mainCpuUsage;
    }

    public double getReadThreadCPUTime() {
        return readThreadCPUTime;
    }

    public void setReadThreadCPUTime(double readThreadCPUTime) {
        this.readThreadCPUTime = readThreadCPUTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getLobName() {
        return lobName;
    }

    public void setLobName(String lobName) {
        this.lobName = lobName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }



}
