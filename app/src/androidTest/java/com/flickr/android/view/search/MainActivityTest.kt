package com.flickr.android.view.search


import android.widget.SearchView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.flickr.android.R
import com.flickr.android.view.MainActivity
import com.google.common.truth.Truth.assertThat
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java, /* initialTouchMode= */ true, /* launchActivity= */ false
    )



 @Test
    fun TestMainActivity_searchItem_is_displayed() {

     activityTestRule.launchActivity(/* startIntent= */ null)
     val title = activityTestRule.activity.title
     // Verify that the activity label is correct as a proxy to verify TalkBack will announce the
     // correct string when it's read out.
     assertThat(title).isEqualTo("Flickr Search")
    }




}