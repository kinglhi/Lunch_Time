package com.example.lunchtime

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId

open class BaseTest {
    fun fullOrderFlow() {
        // Launch the main activity
        launchActivity<MainActivity>()
        // Start order
        Espresso.onView(withId(R.id.start_order_btn)).perform(ViewActions.click())
        // Select entree item
        Espresso.onView(withId(R.id.cauliflower)).perform(ViewActions.click())
        // Move to next fragment
        Espresso.onView(withId(R.id.next_button)).perform(ViewActions.click())
        // Select side item
        Espresso.onView(withId(R.id.salad)).perform(ViewActions.click())
        // Move to next fragment
        Espresso.onView(withId(R.id.next_button)).perform(ViewActions.click())
        // Select accompaniment item
        Espresso.onView(withId(R.id.bread)).perform(ViewActions.click())
        // Move to next fragment
        Espresso.onView(withId(R.id.next_button)).perform(ViewActions.click())
    }
}