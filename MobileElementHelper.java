package com.mmt.travel.app.androidMain.utilities.pageELementsUtils;

import android.app.Activity;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.makemytrip.R;
import com.mmt.travel.app.hotel.util.HotelUtils;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Collection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by MMT6054 on 16-Aug-17.
 */

public class MobileElementHelper {

    //for getting activity instance on running activity in test class to use activity and its components info
    public static Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Activity currentActivity = null;
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (HotelUtils.isNotEmptyCollection(resumedActivities)
                        && resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });
        return activity[0];
    }

    //for swiping top to bottom for any open page
    public static void swipeFromTopToBottom(Matcher<View> bestDeal, int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            onView(bestDeal)
                    .perform(withCustomConstraints(swipeUp(), isDisplayingAtLeast(85)));
            Thread.sleep(1000);
        }
        SystemClock.sleep(4000);
        onView(withText("Super Offers")).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }



    //custom constraint for scrolling page up down
    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }


    public static final class ScrollToPositionViewAction implements ViewAction {
        private final int position;

        public ScrollToPositionViewAction(int position) {
            this.position = position;
        }

        public Matcher<View> getConstraints() {
            return Matchers.allOf(new Matcher[]{
                    ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed()
            });
        }

        public String getDescription() {
            return "scroll RecyclerView to position: " + this.position;
        }

        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.scrollToPosition(this.position);
        }

    }

    //verifying RecyclerView items and its functions
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
