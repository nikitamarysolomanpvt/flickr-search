package com.flickr.android.view.search


import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.widget.SearchView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onIdle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.flickr.android.R
import com.flickr.android.view.MainActivity
import com.google.common.truth.Truth.assertThat
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
lateinit var appContext : Context

    private val activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java, /* initialTouchMode= */ true, /* launchActivity= */ false
    )

    @Before
    fun setUp() {
     appContext = InstrumentationRegistry.getInstrumentation().targetContext

    }



    @Test
    fun questionsFragmentTest_question_number_is_displayed() {

        activityTestRule.launchActivity(/* startIntent= */ null)
        onView(withId(R.id.search)).perform(click())  //open the searchView
        onView(
            withId(
                appContext.resources.getIdentifier(
                    "search_src_text",
                    "id", "android"
                )
            )
        ).perform(typeText("c"), closeSoftKeyboard())
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        val mIdl = RecyclerViewIdlingRes("idlingRecyclerView")
        IdlingRegistry.getInstance().register(mIdl)
        val fragment: SearchItemListFragment =
            activityTestRule.activity.getSupportFragmentManager().fragments[0].childFragmentManager.fragments[0] as SearchItemListFragment
        (fragment).registerOnCallBack(mIdl)
        onIdle()
        IdlingRegistry.getInstance().unregister(mIdl)
        onView(allOf( withId(R.id.search_item_rv)))
            .check(
                matches(isDisplayed())
            )
        onView( CoreMatchers.allOf(
            withId(R.id.search_item_rv),
            isDescendantOfA(withId(R.id.text_title))
        ))
        onView(allOf(withId(R.id.text_title) ,isDisplayed()))
    }


    fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Change view text"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
                view.query
            }
        }
    }


 @Test
    fun TestMainActivity_searchItem_is_displayed() {

     activityTestRule.launchActivity(/* startIntent= */ null)
     val title = activityTestRule.activity.title
     // Verify that the activity label is correct as a proxy to verify TalkBack will announce the
     // correct string when it's read out.
     assertThat(title).isEqualTo("Flickr Search")
    }
}
