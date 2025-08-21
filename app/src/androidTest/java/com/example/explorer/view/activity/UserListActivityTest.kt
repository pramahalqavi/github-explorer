package com.example.explorer.view.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
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
class UserListActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(UserListActivity::class.java)

    @Inject
    lateinit var fakeRepo: FakeGithubRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun userList_isDisplayed_whenFetchUsersSuccess() {
        onView(withId(R.id.rv_users))
            .check(matches(isDisplayed()))

        onView(withText("alice"))
            .check(matches(isDisplayed()))

        onView(withText("bob"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun errorState_isDisplayed_whenFetchUsersError() {
        FakeGithubRepository.shouldFailUsers = true
        activityRule.scenario.recreate()

        onView(withId(R.id.tv_status))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.error_message)))

        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))

        FakeGithubRepository.shouldFailUsers = false

        onView(withId(R.id.btn_retry))
            .perform(click())

        onView(withId(R.id.rv_users))
            .check(matches(isDisplayed()))

        onView(withText("alice"))
            .check(matches(isDisplayed()))

        onView(withText("bob"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun searchResult_isDisplayed_whenSearchUserSuccess() {
        FakeGithubRepository.shouldFailSearch = false
        FakeGithubRepository.shouldFailUsers = false
        onView(withId(R.id.et_search_bar)).perform(typeText("alice"))

        onView(withId(R.id.rv_users)).check(matches(hasChildCount(2)))

        Thread.sleep(1200)

        onView(withId(R.id.rv_users)).check(matches(hasChildCount(1)))
    }

    @Test
    fun errorState_isDisplayed_whenSearchUserError() {
        FakeGithubRepository.shouldFailSearch = true
        activityRule.scenario.recreate()

        onView(withId(R.id.et_search_bar)).perform(typeText("alice"))

        onView(withId(R.id.rv_users)).check(matches(hasChildCount(2)))

        Thread.sleep(1200)

        onView(withId(R.id.tv_status))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.error_message)))

        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))
    }
}