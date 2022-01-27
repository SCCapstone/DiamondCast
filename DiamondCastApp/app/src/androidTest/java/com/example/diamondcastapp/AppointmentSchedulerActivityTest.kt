package com.example.diamondcastapp

import android.widget.CalendarView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

import org.junit.Test
import java.text.SimpleDateFormat
import java.time.Instant.now
import java.time.LocalDate.now
import java.util.*


class AppointmentSchedulerActivityTest {
    private lateinit var scenario: ActivityScenario<AppointmentSchedulerActivity>
    @Test
    fun isSetDateTextViewDisplayed() {
        scenario = launchActivity()
        val formatter = SimpleDateFormat("MM-dd-yy",Locale.getDefault())
        val date = formatter.format(Calendar.getInstance().time)
        onView(withId(R.id.setDate)).check(matches(withText("Choose a Date")))

    }
}