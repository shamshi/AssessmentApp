package com.shamseer.assessmentapp.ui.activities.details

import android.content.Context
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.shamseer.assessmentapp.CustomAssertions
import com.shamseer.assessmentapp.CustomMatchers
import com.shamseer.assessmentapp.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

/**
 * Created by Shamseer on 5/30/20.
 */
class DetailsActivityTest {

    private var appContext: Context? = null

    @get:Rule
    var activityRule = ActivityTestRule(DetailsActivity::class.java)

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun isPresent_DetailsView_ReturnsTrue() {
        onView(ViewMatchers.withId(R.id.rvDetails))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(ViewMatchers.withId(R.id.fabEmail))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(ViewMatchers.withId(R.id.emailLoader))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun checkItem() {
        Espresso.onView(ViewMatchers.withId(R.id.rvDetails))
            .check(ViewAssertions.matches(CustomMatchers.withItemCount(1)))
    }

    @Test
    fun checkItemWithViewAssertion() {
        Espresso.onView(ViewMatchers.withId(R.id.rvDetails))
            .check(CustomAssertions.hasItemCount(1))
    }

    @After
    fun tearDown() {
        appContext = null
    }
}