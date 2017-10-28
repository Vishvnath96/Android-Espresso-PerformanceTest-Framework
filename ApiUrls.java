package com.mmt.travel.app.androidMain.NFR.NFRUtils;

/**
 * Created by MMT6054 on 09-Aug-17.
 */

public enum ApiUrls {
    MemoryUsageUrl("http://appnfr.makemytrip.com/androidmemoryusage"),
    CpuUsageUrl("http://appnfr.makemytrip.com/androidcpuusage"),
    NetworkUsageUrl("http://appnfr.makemytrip.com/androidnetworkusage"),
    GetNetworkDataUrl("http://172.16.94.219:8080/getnetworkdata"),
    GetCPUDataUrl("http://172.16.94.219:8080/getcpudata"),
    GetMemoryDataUrl("http://172.16.94.219:8080/getmemorydata");
    String value;
    private ApiUrls(String url) {
        value = url;
    }

}
