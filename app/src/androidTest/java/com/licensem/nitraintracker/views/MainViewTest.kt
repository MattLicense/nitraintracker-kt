package com.licensem.nitraintracker.views

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withSpinnerText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.licensem.nitraintracker.MainActivity
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.SearchActivity
import com.licensem.nitraintracker.test.withTripDestination
import com.licensem.nitraintracker.test.withTripOrigin
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainViewTest {

    var mainActivity : MainActivity? = null

    @Rule @JvmField
    val mainActivityTestRule = ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun setUp() {
        Intents.init()
        mainActivity = mainActivityTestRule.launchActivity(Intent())
    }

    @After
    fun tearDown() {
        Intents.release()
        mainActivity?.finish()
    }

    @Test
    fun testClickSearchTrains() {
        onView(withId(R.id.search_button)).perform(click())
        intended(hasComponent(SearchActivity::class.java.name))
    }

    @Test
    fun testFavourites() {
        onView(withId(R.id.search_button)).perform(click())
        intended(hasComponent(SearchActivity::class.java.name))

        onView(withId(R.id.originSelector)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Bangor"))).perform(click())
        onView(withId(R.id.originSelector)).check(matches(withSpinnerText(containsString("Bangor"))))

        onView(withId(R.id.destinationSelector)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Bangor West"))).perform(click())
        onView(withId(R.id.destinationSelector)).check(matches(withSpinnerText(containsString("Bangor West"))))

        onView(withId(R.id.favourite_button)).perform(click())
        Espresso.pressBack()

        onData(withTripDestination("Bangor West"))
                .inAdapterView(withId(R.id.favourites_list))
        onData(withTripOrigin("Bangor"))
                .inAdapterView(withId(R.id.favourites_list))
                .atPosition(0)
                .perform(click())

        val expIntent = allOf(
                hasComponent(SearchActivity::class.java.name),
                hasExtra("origin","Bangor"),
                hasExtra("destination","Bangor West")
        )
        intended(expIntent)
        onView(withId(R.id.favourite_button)).perform(click())
        Espresso.pressBack()
    }

}
