package com.shamseer.assessmentapp.ui.activities.main

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.shamseer.assessmentapp.CustomAssertions.Companion.hasItemCount
import com.shamseer.assessmentapp.CustomMatchers.Companion.withItemCount
import com.shamseer.assessmentapp.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Shamseer on 5/30/20.
 */
class MainActivityTest {

    private var appContext: Context? = null

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun isPresent_MainView_ReturnsTrue() {
        onView(withId(R.id.rvItems))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun countItems() {
        onView(withId(R.id.rvItems))
            .check(matches(withItemCount(8)))
    }

    @Test
    fun countItemsWithViewAssertion() {
        onView(withId(R.id.rvItems))
            .check(hasItemCount(8))
    }

    @After
    fun tearDown() {
        appContext = null
    }
}