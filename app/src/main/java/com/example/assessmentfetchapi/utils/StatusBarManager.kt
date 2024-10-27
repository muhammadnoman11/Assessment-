package com.example.assessmentfetchapi.utils


import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.Window
import android.view.WindowManager


object StatusBarManager {
    fun changeStatusBarColor(activity: Activity,color: String) {
        val window: Window =activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = android.graphics.Color.parseColor(color)
    }
}