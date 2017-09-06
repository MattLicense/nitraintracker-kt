package com.licensem.nitraintracker.views

import android.content.Intent
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.SearchActivity
import com.licensem.nitraintracker.test.RegexMatcher
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchViewTest {

    var searchActivity: SearchActivity? = null

    @Rule
    @JvmField
    val searchActivityTestRule = ActivityTestRule(SearchActivity::class.java, false, true)

    @Before
    fun setUp() {
        Intents.init()
        searchActivity = searchActivityTestRule.launchActivity(Intent())
    }

    @After
    fun tearDown() {
        Intents.release()
        searchActivity?.finish()
    }

    @Test
    fun testSearch() {
        onView(withId(R.id.originSelector)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Bangor"))).perform(click())
        onView(withId(R.id.originSelector)).check(matches(withSpinnerText(containsString("Bangor"))))

        onView(withId(R.id.destinationSelector)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Bangor"))).perform(click())
        onView(withId(R.id.destinationSelector)).check(matches(withSpinnerText(containsString("Bangor"))))

        onView(withId(R.id.search_button)).check(matches(not(isDisplayed())))
        onView(withId(R.id.search_button)).check(matches(not(isEnabled())))

        onView(withId(R.id.destinationSelector)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Bangor West"))).perform(click())
        onView(withId(R.id.destinationSelector)).check(matches(withSpinnerText(containsString("Bangor West"))))

        onView(withId(R.id.search_button)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(isEnabled()))

        onView(withId(R.id.search_button)).perform(click())

        onData(anything())
            .inAdapterView(withId(R.id.result_view))
            .atPosition(0)
            .onChildView(withId(R.id.service_title))
            .check(matches(withText(RegexMatcher("Service to: [a-zA-Z]+"))))

        onData(anything())
            .inAdapterView(withId(R.id.result_view))
            .atPosition(0)
            .onChildView(withId(R.id.service_detail))
            .check(matches(withText(RegexMatcher("Leaving from platform \\d at (\\d){2}:(\\d){2}, arriving at (\\d){2}:(\\d){2}"))))
    }

}