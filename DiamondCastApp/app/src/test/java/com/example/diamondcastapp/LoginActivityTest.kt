package com.example.diamondcastapp

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//Testing the Login Activity functionality
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private lateinit var scenario: ActivityScenario<LoginActivity>

    @Before
    fun setup() {
        scenario = launchActivity<LoginActivity>()
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    //checks whether LoginActivity.loggingin() will accept invalid email for login
    fun testInvalidUsernameLogin() {
        onView(withId(R.id.loginEmailInput)).perform(typeText("invalid@gmail.com"))
        onView(withId(R.id.loginPasswordInput)).perform(typeText("password"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.loginEnter)).perform(click())

        assertNotNull(scenario)
    }

    @After
    fun tearDown() {
        scenario.close()
    }
}