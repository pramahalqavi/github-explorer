package com.example.explorer.view.activity

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.explorer.repository.FakeGithubRepository
import com.example.githubexplorer.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class UserDetailActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule<UserDetailActivity>(
        Intent(ApplicationProvider.getApplicationContext(), UserDetailActivity::class.java).apply {
            putExtra("USERNAME", "alice")
        }
    )

    @Inject
    lateinit var fakeRepo: FakeGithubRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun userDetail_isDisplayed_whenGetDetailSuccess() {
        onView(withId(R.id.rv_content))
            .check(matches(isDisplayed()))

        onView(withText("alice"))
            .check(matches(isDisplayed()))

        onView(withText("Lou Miranda"))
            .check(matches(isDisplayed()))

        onView(withText("repo1"))
            .check(matches(isDisplayed()))

        onView(withText("repo2"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun errorState_isDisplayed_whenGetDetailError() {
        FakeGithubRepository.shouldFailUserDetails = true
        activityRule.scenario.recreate()

        onView(withId(R.id.tv_status))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.error_message)))

        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))

        FakeGithubRepository.shouldFailUserDetails = false
        FakeGithubRepository.shouldFailRepositories = true

        onView(withId(R.id.btn_retry))
            .perform(click())

        onView(withId(R.id.rv_content))
            .check(matches(isDisplayed()))

        onView(withText("alice"))
            .check(matches(isDisplayed()))

        onView(withText("Lou Miranda"))
            .check(matches(isDisplayed()))
    }
}