package com.example.diamondcastapp

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//Testing the Login Activity functionality

class LoginActivityTest {

    private lateinit var scenario: ActivityScenario<LoginActivity>
    private lateinit var scenarioHome: ActivityScenario<ClientHomeScreenActivity>



    @Test
    //tests whether user is input matches what is displayed
    fun testUserInputMatchesLogin() {
        scenario = launchActivity()
        onView(withId(R.id.loginEmailInput)).perform(typeText("invalid@gmail.com"))
        closeSoftKeyboard()
        onView(withId(R.id.loginEmailInput)).check(matches(withText("invalid@gmail.com")))
        onView(withId(R.id.loginPasswordInput)).perform(typeText("password"))
        closeSoftKeyboard()
        onView(withId(R.id.loginPasswordInput)).check(matches(withText("password")))
    }
    @Test
    fun testLoginBtnIsClickable() {
        scenario = launchActivity()
        onView(withId(R.id.loginEnter)).check(matches(isClickable()))
    }

}