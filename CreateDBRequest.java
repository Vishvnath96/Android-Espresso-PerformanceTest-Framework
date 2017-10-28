package com.mmt.travel.app.androidMain.NFR.NFRUtils;

import android.os.SystemClock;

import com.mmt.travel.app.common.util.GsonUtils;
import com.mmt.travel.app.common.util.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by MMT6054 on 08-Aug-17.
 */

public class CreateDBRequest {


    private static final String CONN = "CreateDBConnection";

    //making api request to open connection with the qa DB and inserting data into androidmemoryusage table in database
    public void createAPIRequest(Object obj, ApiUrls apiUrl) {
        OkHttpClient client = new OkHttpClient();

        String requestString = GsonUtils.getInstance().serializeToJson(obj);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),requestString);

        Request.Builder builder = new Request.Builder();
        builder.addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .post(requestBody)
                .url(apiUrl.value);
        final Request request = builder.build();
        SystemClock.sleep(2000);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    CreateDBRequest.this.onFailure();
                System.out.println("Failed to dump data in db");

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CreateDBRequest.this.onResponse();
                System.out.println("data dumped successfully");
            }
        });
    }


    private void onResponse() {
        LogUtils.info(CONN,"Connection successfully created");
    }

    private void onFailure() {
        LogUtils.info(CONN,"Connection Failure ,check connection api for details info");
        LogUtils.error(CONN,CreateDBRequest.this.toString());
    }

}
