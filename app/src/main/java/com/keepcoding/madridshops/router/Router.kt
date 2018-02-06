package com.keepcoding.madridshops.router

import android.content.Intent
import com.keepcoding.madridshops.activity.MainActivity
import com.keepcoding.madridshops.activity.PicassoActivity


class Router {
    fun navigateFromMainActivityToPicassoActivity(main: MainActivity) {
        main.startActivity(Intent(main, PicassoActivity::class.java))
    }

}