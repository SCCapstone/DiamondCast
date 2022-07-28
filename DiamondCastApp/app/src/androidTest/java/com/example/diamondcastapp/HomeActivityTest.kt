package com.example.diamondcastapp

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

import org.junit.Test

class HomeActivityTest {
    private lateinit var scenario: ActivityScenario<ClientHomeScreenActivity>

    @Test
    fun testGoToAppointmentBtnIsClickable() {
        scenario = launchActivity()
        Espresso.onView(ViewMatchers.withId(R.id.goToAppointmentBtn))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
    }

}