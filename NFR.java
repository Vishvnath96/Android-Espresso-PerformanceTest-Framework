package com.mmt.travel.app.androidMain.NFR.NFRFuntions;

import java.sql.Statement;

/**
 * Created by MMT6054 on 09-Jul-17.
 */
/*implements default behaviour for the interface common to all classes as appropriate;
declares an interface for accessing and managing its child components*/
public interface NFR
{
    public void perform(Functionality functionality, String statusArray[], Statement statement);
}
