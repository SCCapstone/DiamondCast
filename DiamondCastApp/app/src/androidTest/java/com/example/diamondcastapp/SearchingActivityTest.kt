package com.example.diamondcastapp

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test

class SearchingActivityTest {
    private lateinit var scenario: ActivityScenario<SearchingActivity>

    @Test
    fun testUserInputMatchesSearchBar() {
        scenario = launchActivity()
        onView(withId(R.id.search_bar)).perform(typeText("Contractor"))
        closeSoftKeyboard()
        onView(withId(R.id.search_bar)).check(ViewAssertions.matches(ViewMatchers.withText("Contractor")))
    }

    @Test
    fun testLoginBtnIsClickable() {
        scenario = launchActivity()
        Espresso.onView(ViewMatchers.withId(R.id.searchImageButton))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
    }
}