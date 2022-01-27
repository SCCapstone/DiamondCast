package com.example.diamondcastapp;


import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.widget.EditText;

import androidx.annotation.ContentView;
import androidx.test.core.app.ActivityScenario;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;





import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> rule = new ActivityScenarioRule<>(LoginActivity.class);


    @Test
    public void TestValidUserLogin() {
        Espresso.onView(withId(R.id.loginEmailInput));
    }
    @Test
    public void TestInvalidUserLogin() {
        ActivityScenario scenario = rule.getScenario();



    }
}
