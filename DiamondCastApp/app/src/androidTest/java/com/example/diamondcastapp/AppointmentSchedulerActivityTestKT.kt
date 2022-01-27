package com.example.diamondcastapp

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test


class AppointmentSchedulerActivityTestKT {
    @Test
    fun isCalendarDisplayed() {
        val scenario = launchActivity<AppointmentSchedulerActivity>()
        onView(withId(R.id.calendar)).check(matches(isDisplayed()))
    }
}