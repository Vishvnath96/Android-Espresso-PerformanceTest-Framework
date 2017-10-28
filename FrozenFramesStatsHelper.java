package com.mmt.travel.app.androidMain.NFR.AggregateFrameStatsImpl;

import android.os.Build;
import android.support.annotation.NonNull;

import com.mmt.travel.app.androidMain.NFR.AggregateFrameStatsPojo.FrozenFramesStatsManager;
import com.mmt.travel.app.androidMain.NFR.NFRFuntions.Functionality;
import com.mmt.travel.app.androidMain.NFR.NFRFuntions.NFR;
import com.mmt.travel.app.androidMain.NFR.NFRFuntions.NFRHelper;
import com.mmt.travel.app.androidMain.NFR.NFRFuntions.adbCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by MMT6054 on 30-Jul-17.
 */

public class FrozenFramesStatsHelper implements adbCommands,NFR {
    public static Map<String , FrozenFramesStatsManager> frameStatsMap = new HashMap<>();
    BufferedReader br = null;

    //collecting frozen frames data by using adb commands as as process run
    private void collectFrozenFrame(String udid ,String methodName,String LOB){
        FrozenFramesStatsManager aggregateFrameStatsManager;
        try {
            Process process = Runtime.getRuntime().exec(adbCommands.frameStatsCommands);
            process.waitFor();
            aggregateFrameStatsManager = new FrozenFramesStatsManager();
            if(udid != null) {
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                extractFrozenFramesData(aggregateFrameStatsManager);
            }
            else if(udid == null){
                aggregateFrameStatsManager.setTotalFrameRendered(0);
                aggregateFrameStatsManager.setJankyFrameCount(0);
                aggregateFrameStatsManager.setGetJankyFramePercentage(0);
                aggregateFrameStatsManager.setNintyPercentileTimeInms(0);
                aggregateFrameStatsManager.setNintyFivePercentileTimeInms(0);
                aggregateFrameStatsManager.setNintyNinePercentileTimeInms(0);
                aggregateFrameStatsManager.setSlowUIThreadCount(0);
                aggregateFrameStatsManager.setMissedVsyncCount(0);
                aggregateFrameStatsManager.setSlowIssueDrawCommands(0);
                aggregateFrameStatsManager.setSlowBitmapUploadCount(0);
            }
            frameStatsMap.put(methodName + "%%" + NFRHelper.getDeviceName(udid) + "%%" + NFRHelper.getAPKNameAndVersion() + "%%"
                    + LOB + "%%" + NFRHelper.getCurrentTimeStamp(),aggregateFrameStatsManager);

        }catch (Exception e){

        }
    }

    public  void extractFrozenFramesData(FrozenFramesStatsManager aggregateFrameStatsManager) {
        String[] frame_array = null;
        String line;


        try {
            while((line = br.readLine()) != null){
                if(line.contains("Total frames rendered:")){
                    frame_array = line.split("\\s+");
                    System.out.println(frame_array);
                    aggregateFrameStatsManager.setTotalFrameRendered(Integer.parseInt(frame_array[3]));
                }
                else if(line.contains("Janky frames")){
                    frame_array = line.split("\\s+");
                    float jankyFramePercent = Float.parseFloat((String) frame_array[3].subSequence(1,5));
                    aggregateFrameStatsManager.setJankyFrameCount(Integer.parseInt(frame_array[2]));
                    aggregateFrameStatsManager.setGetJankyFramePercentage(Float.parseFloat((String) frame_array[3].subSequence(1,5)));
                }
                else if(line.contains("90th percentile:")){
                    frame_array = line.split("\\s+");
                    aggregateFrameStatsManager.setNintyPercentileTimeInms(Integer.parseInt(frame_array[2].replaceAll("ms","")));
                }
                else if(line.contains("95th percentile:")){
                    frame_array = line.split("\\s+");
                    aggregateFrameStatsManager.setNintyFivePercentileTimeInms(Integer.parseInt(frame_array[2].replaceAll("ms","")));
                }
                else if(line.contains("99th percentile:")){
                    frame_array = line.split("\\s+");
                    aggregateFrameStatsManager.setNintyNinePercentileTimeInms(Integer.parseInt(frame_array[2].replaceAll("ms","")));
                }
                else if(line.contains("Number Missed Vsync:")){
                    frame_array = line.split("\\s+");
                    aggregateFrameStatsManager.setMissedVsyncCount(Integer.parseInt(frame_array[3]));
                }
                else if(line.contains("Number Slow UI thread:")){
                    frame_array = line.split("\\s+");
                    aggregateFrameStatsManager.setSlowUIThreadCount(Integer.parseInt(frame_array[4]));
                }
                else if(line.contains("Number Slow bitmap uploads:")){
                    frame_array = line.split("\\s+");
                    aggregateFrameStatsManager.setSlowBitmapUploadCount(Integer.parseInt(frame_array[4]));
                }
                else if(line.contains("Number Slow issue draw commands:")){
                    frame_array = line.split("\\s+");
                    aggregateFrameStatsManager.setSlowIssueDrawCommands(Integer.parseInt(frame_array[5]));
                }
                else if(line.contains("Caches:")){
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(aggregateFrameStatsManager);
    }

    //for publishing data in qa DB
    public static void publish(Statement statement){
        Set keySet = frameStatsMap.keySet();
        Iterator iterator = keySet.iterator();
        String key = null;
        String queryStatement;
        while(iterator.hasNext()){
            key = (String) iterator.next();
            if (key != null) {
                String[] key_array = key.split("%%");
                queryStatement = String.format(adbCommands.queryToInsertFrozenFrameData, getObjectValues(key, key_array));

                try {
                    //statement = getDBConnection().createStatement();
                    statement.execute(queryStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                iterator.remove();
                frameStatsMap.remove(key);
            }
        }
        //closeDBConnection();
    }

    //get all data and fill in single pojo request
    @NonNull
    private static Object[] getObjectValues(String key, String[] key_array) {
        return new Object[]{key_array[0], key_array[1], key_array[2], key_array[3],
                key_array[4], frameStatsMap.get(key).getTotalFrameRendered(), frameStatsMap.get(key).getJankyFrameCount(),
                frameStatsMap.get(key).getGetJankyFramePercentage(), frameStatsMap.get(key).getNintyPercentileTimeInms(),
                frameStatsMap.get(key).getNintyFivePercentileTimeInms(), frameStatsMap.get(key).getNintyNinePercentileTimeInms(),
                frameStatsMap.get(key).getMissedVsyncCount(), frameStatsMap.get(key).getSlowUIThreadCount(),
                frameStatsMap.get(key).getSlowBitmapUploadCount(), frameStatsMap.get(key).getSlowIssueDrawCommands()};
    }

    @Override
    public void perform(Functionality functionality, String[] statusArray, Statement statement) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (functionality.toString().toUpperCase()) {
                case "COLLECT":
                    collectFrozenFrame(statusArray[0], statusArray[1], statusArray[2]);
                    break;
                case "PUBLISH":
                    publish(statement);
                    break;
                default:
                    System.out.println("Invalid input received");
            }

        }
        else
        {
            System.out.println("Device api version less than 23 not supported frame stats data!... Use greater than or equals to  23 version for Frame Stats data!!");
        }
    }



    public static void main(String[] args) throws IOException, InterruptedException {
        Statement statement;
        FrozenFramesStatsHelper ag = new FrozenFramesStatsHelper();
        String testSetParam[] = {NFRHelper.getUDIDOfConnectedDevice(), "testHomePage", "CMN"};
        ag.perform(Functionality.COLLECT ,testSetParam ,null);
        ag.perform(Functionality.PUBLISH,testSetParam,null);

    }


}
