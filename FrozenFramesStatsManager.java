package com.mmt.travel.app.androidMain.NFR.AggregateFrameStatsPojo;

/**
 * Created by MMT6054 on 29-Jul-17.
 */

public class FrozenFramesStatsManager {
    private int totalFrameRendered;
    private int jankyFrameCount;
    private float getJankyFramePercentage;
    private int nintyPercentileTimeInms;
    private int nintyFivePercentileTimeInms;
    private int nintyNinePercentileTimeInms;
    private int missedVsyncCount;
    private int slowUIThreadCount;
    private int slowBitmapUploadCount;
    private int slowIssueDrawCommands;


    public int getTotalFrameRendered() {
        return totalFrameRendered;
    }

    public void setTotalFrameRendered(int totalFrameRendered) {
        this.totalFrameRendered = totalFrameRendered;
    }

    public int getJankyFrameCount() {
        return jankyFrameCount;
    }

    public void setJankyFrameCount(int jankyFrameCount) {
        this.jankyFrameCount = jankyFrameCount;
    }

    public float getGetJankyFramePercentage() {
        return getJankyFramePercentage;
    }

    public void setGetJankyFramePercentage(float getJankyFramePercentage) {
        this.getJankyFramePercentage = getJankyFramePercentage;
    }

    public int getNintyPercentileTimeInms() {
        return nintyPercentileTimeInms;
    }

    public void setNintyPercentileTimeInms(int nintyPercentileTimeInms) {
        this.nintyPercentileTimeInms = nintyPercentileTimeInms;
    }

    public int getNintyFivePercentileTimeInms() {
        return nintyFivePercentileTimeInms;
    }

    public void setNintyFivePercentileTimeInms(int nintyFivePercentileTimeInms) {
        this.nintyFivePercentileTimeInms = nintyFivePercentileTimeInms;
    }

    public int getNintyNinePercentileTimeInms() {
        return nintyNinePercentileTimeInms;
    }

    public void setNintyNinePercentileTimeInms(int nintyNinePercentileTimeInms) {
        this.nintyNinePercentileTimeInms = nintyNinePercentileTimeInms;
    }

    public int getMissedVsyncCount() {
        return missedVsyncCount;
    }

    public void setMissedVsyncCount(int missedVsyncCount) {
        this.missedVsyncCount = missedVsyncCount;
    }

    public int getSlowUIThreadCount() {
        return slowUIThreadCount;
    }

    public void setSlowUIThreadCount(int slowUIThreadCount) {
        this.slowUIThreadCount = slowUIThreadCount;
    }

    public int getSlowBitmapUploadCount() {
        return slowBitmapUploadCount;
    }

    public void setSlowBitmapUploadCount(int slowBitmapUploadCount) {
        this.slowBitmapUploadCount = slowBitmapUploadCount;
    }

    public int getSlowIssueDrawCommands() {
        return slowIssueDrawCommands;
    }

    public void setSlowIssueDrawCommands(int slowIssueDrawCommands) {
        this.slowIssueDrawCommands = slowIssueDrawCommands;
    }
}
