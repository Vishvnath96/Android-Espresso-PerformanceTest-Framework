package com.mmt.travel.app.androidMain.utilities.instructionUtils;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.view.View;

import com.makemytrip.R;
import com.mmt.travel.app.androidMain.utilities.conditionWatcher.Instruction;
import com.mmt.travel.app.mobile.MMTApplication;

/**
 * Created by MMT6054 on 16-Aug-17.
 * Ui thread will wait until desired view or elements are visible on launching activity
 */
public class BaseInstruction extends Instruction {

    int findById ;
    public BaseInstruction(int  findById){
        this.findById = findById;
    }

    @Override
    public String getDescription() {
        return "View identify with desired property";
    }

    @Override
    public boolean checkCondition() {
        Activity activity = ((MMTApplication)
                InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
        if (activity == null) return false;
        //TextView btnStart = (TextView) activity.findViewById(R.id.BtnSearch);
        View view = activity.findViewById(findById);
        return view != null;
    }
}
