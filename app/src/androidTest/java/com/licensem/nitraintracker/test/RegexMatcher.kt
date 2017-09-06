package com.licensem.nitraintracker.test

import android.support.test.espresso.matcher.BoundedMatcher
import com.licensem.nitraintracker.model.FavouriteTrip
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RegexMatcher(private val regex: String) : TypeSafeMatcher<String>() {

    override fun describeTo(description: Description) {
        description.appendText("matches regular expression=`$regex`")
    }

    public override fun matchesSafely(string: String): Boolean {
        return string.matches(regex.toRegex())
    }

    companion object {
        fun matchesRegex(regex: String): RegexMatcher {
            return RegexMatcher(regex)
        }
    }
}

fun withTripOrigin(origin: String): Matcher<Any> {
    return object : BoundedMatcher<Any, FavouriteTrip>(FavouriteTrip::class.java) {
        public override fun matchesSafely(trip: FavouriteTrip): Boolean {
            return trip.origin == origin
        }

        override fun describeTo(description: Description) {
            description.appendText("with name: ")
        }
    }
}

fun withTripDestination(destination: String): Matcher<Any> {
    return object : BoundedMatcher<Any, FavouriteTrip>(FavouriteTrip::class.java) {
        public override fun matchesSafely(trip: FavouriteTrip): Boolean {
            return trip.destination == destination
        }

        override fun describeTo(description: Description) {
            description.appendText("with name: ")
        }
    }
}
