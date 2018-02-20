package com.keepcoding.madridshops.router

import android.content.Intent
import android.util.Log
import com.keepcoding.madridshops.activity.DetailActivity
import com.keepcoding.madridshops.activity.MainActivity
import com.keepcoding.madridshops.activity.PicassoActivity
import com.keepcoding.madridshops.domain.model.Shop


class Router {
    fun navigateFromMainActivityToPicassoActivity(main: MainActivity) {
        main.startActivity(Intent(main, PicassoActivity::class.java))
    }

    fun navigateFromMainActivityToDetailActivity(main: MainActivity, shop: Shop) {
        Log.d("Hola", "navigateFromMainActivityToDetailActivity")
        // main.startActivity(Intent(main, DetailActivity::class.java))
        main.startActivity(DetailActivity.intent(main, "Bla, bla, ...", shop))
    }
}