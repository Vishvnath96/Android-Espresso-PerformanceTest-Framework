package com.mmt.travel.app.androidMain.NFR.NFRFuntions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by MMT6054 on 22-Aug-17.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface Lob{
    String stringValue();
}