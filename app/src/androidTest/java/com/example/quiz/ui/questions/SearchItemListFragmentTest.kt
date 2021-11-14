package com.example.quiz.ui.questions

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quiz.R
import com.example.quiz.ui.MainActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
class SearchItemListFragmentTest {


    @Test
    fun questionsFragmentTest_question_number_is_displayed() {
        ActivityScenario.launch(
            MainActivity::class.java
        ).use {
            onView(withId(R.id.questions_rv)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(0)
            )
            onView(
                RecyclerViewMatcher.atPositionOnView(
                    recyclerViewId = R.id.questions_rv,
                    position = 0,
                    targetViewId = R.id.descriptionTv
                )
            ).check(
                matches(withText("Question no.: 1"))
            )
        }
    }




    @Test
    fun questionsFragmentTest_submit_button_click_has_displayed_preview_fragment_title () {
        ActivityScenario.launch(
            MainActivity::class.java
        ).use {
            onView(
                allOf(
                    instanceOf(AppCompatTextView::class.java),
                    withParent(withId(R.id.toolbar))
                )
            )
                .check(matches(withText("Questions Answere Preview")))


        }
    }







    @Test
    fun testFractionInput_withNoInput_hasCorrectPendingAnswerType1() {
        ActivityScenario.launch(
            MainActivity::class.java
        ).use {
            onView(withId(R.id.questions_rv)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(0)
            )
            onView(
                RecyclerViewMatcher.atPositionOnView(
                    recyclerViewId = R.id.questions_rv,
                    position = 0,
                    targetViewId = R.id.text_title
                )
            ).check(
                matches(withId(R.id.text_title))
            )

        }
    } @Test
    fun questionsFragmentTest_with_multiple_choice_view_visible() {
        ActivityScenario.launch(
            MainActivity::class.java
        ).use {
            onView(withId(R.id.questions_rv)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(0)
            )
            onView(
                RecyclerViewMatcher.atPositionOnView(
                    recyclerViewId = R.id.questions_rv,
                    position = 0,
                    targetViewId = R.id.text_title
                )
            ).check(
                matches(withId(R.id.text_title))
            )

        }
    }

    @Test
    fun questionsFragmentTest_with_image_view_visible() {
        ActivityScenario.launch(
            MainActivity::class.java
        ).use {
            onView(withId(R.id.questions_rv)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(1)
            )
            onView(
                RecyclerViewMatcher.atPositionOnView(
                    recyclerViewId = R.id.questions_rv,
                    position = 1,
                    targetViewId = R.id.image_poster
                )
            ).check(
                matches(withId(R.id.image_poster))
            )
            sleep(1000);

        }


    }

}