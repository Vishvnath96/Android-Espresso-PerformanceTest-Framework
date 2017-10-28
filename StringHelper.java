package com.mmt.travel.app.androidMain.utilities;

import java.util.Date;
import java.util.Random;

/**
 * Created by MMT6054 on 26-Jul-17.
 */

public class StringHelper {

    //for generating unique string name
    public static String uniqueString(){
        Date date = new Date(System.currentTimeMillis());
        String name = date.toString();
        Random random = new Random();
        String tag = Long.toString(Math.abs(random.nextLong()),36);
        String fileName = tag.substring(0,6);
        return fileName;
    }

}
