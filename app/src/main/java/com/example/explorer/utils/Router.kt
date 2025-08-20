package com.example.explorer.utils

import android.content.Context
import android.content.Intent
import com.example.explorer.view.activity.UserDetailActivity

object Router {
    fun routeToUserDetail(context: Context, username: String) {
        context.startActivity(Intent(context, UserDetailActivity::class.java).apply {
            putExtra(UserDetailActivity.EXTRA_USERNAME, username)
        })
    }
}