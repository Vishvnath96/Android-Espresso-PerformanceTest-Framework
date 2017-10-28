package com.mmt.travel.app.androidMain.NFR.NFRUtils;

/**
 * Created by MMT6054 on 09-Aug-17.
 */

public enum ApiUrls {
    MemoryUsageUrl("http://172.16.94.219/androidmemoryusage"),
    CpuUsageUrl("http://172.16.94.219/androidcpuusage"),
    NetworkUsageUrl("http://172.16.94.219/androidnetworkusage"),
    GetNetworkDataUrl("http://172.16.94.219/getnetworkdata"),
    GetCPUDataUrl("http://172.16.94.219/getcpudata"),
    GetMemoryDataUrl("http://172.16.94.219/getmemorydata");
    String value;
    private ApiUrls(String url) {
        value = url;
    }

}
