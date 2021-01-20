package com.app.messengermvp.ui.activities;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import com.app.messengermvp.R;
import com.app.messengermvp.ui.mvp.model.FriendListModel;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UserProfileActivityTest {


    @Rule
    public ActivityTestRule<UserProfileActivity> activityTestRule =
            new ActivityTestRule<>(UserProfileActivity.class);

    /**
     * Register IdlingResource resource to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize test actions.
     */

//    @Before // 1
//    public void registerIdlingResource() {
//
//    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
//    @After
//    public void unregisterIdlingResource() {
//    }

    @Test // 2
    public void checkStaticView() {
        // verify default empty text message
        Espresso.onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        //                                |--------------------------|
        //|----------------------------| find a view | using swipe_msg_tv id
        //check visibility of view on screen <-------|
    }

    @Test
    public void checkRecyclerViewVisibility() {


        // 4 verify recycler view is displayed
        Espresso.onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        // 5 perform click on item at 0th position
//        Espresso.onView(withId(R.id.recyclerView))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // 6 verify the toast text

//        UserProfileActivity activity = activityTestRule.getActivity();
//        Espresso.onView(withText("Title : 'IT' Rating : '7.6'")).
//                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
//                check(matches(isDisplayed()));
    }
}