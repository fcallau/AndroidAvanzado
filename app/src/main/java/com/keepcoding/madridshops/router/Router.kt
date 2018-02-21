package com.keepcoding.madridshops.router

import android.content.Intent
import android.util.Log
import com.keepcoding.madridshops.activity.DetailActivity
import com.keepcoding.madridshops.activity.MainActivity
import com.keepcoding.madridshops.activity.MapListActivity
import com.keepcoding.madridshops.activity.PicassoActivity
import com.keepcoding.madridshops.domain.model.Shop


class Router {
    fun navigateFromMapListActivityToPicassoActivity(main: MapListActivity) {
        main.startActivity(Intent(main, PicassoActivity::class.java))
    }

    fun navigateFromMainActivityToMapListActivity(main: MainActivity, textButton: String) {
        Log.d("navigate", "navigateFromMainActivityToMapListActivity")
        main.startActivity(MapListActivity.intent(main, textButton))
    }

    /*fun navigateFromMainActivityToDetailActivity(main: MainActivity, shop: Shop) {
        main.startActivity(DetailActivity.intent(main, "Bla, bla, ...", shop))
    }*/

    fun navigateFromMapListActivityToDetailActivity(main: MapListActivity, shop: Shop) {
        main.startActivity(DetailActivity.intent(main, "Bla, bla, ...", shop))
    }
}