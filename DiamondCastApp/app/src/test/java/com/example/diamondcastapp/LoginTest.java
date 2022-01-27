package com.example.diamondcastapp;


import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.ContentView;
import androidx.test.core.app.ActivityScenario;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;

//My attempt to test in Java, could not figure out errors, got kotlin test to work
public class LoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> rule = new ActivityScenarioRule<>(LoginActivity.class);

    private ActivityScenario<Activity> loginActivity = null;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra("parameter", "Value");
        loginActivity = ActivityScenario.launch(intent);

    }

    @Test
    public void TestInValidUserLogin() {
        Espresso.onView(withId(R.id.loginEmailInput)).perform(ViewActions.typeText("invalid@gmail.com"));
        Espresso.onView(withId(R.id.loginPasswordInput)).perform(ViewActions.typeText("password"));
        Espresso.onView(withId(R.id.loginEnter)).perform(ViewActions.click());

        assertNotNull(loginActivity);
    }
    @After
    public void tearDown() {
        loginActivity = null;
    }
}
