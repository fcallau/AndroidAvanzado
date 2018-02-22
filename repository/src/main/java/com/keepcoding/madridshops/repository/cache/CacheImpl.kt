package com.keepcoding.madridshops.repository.cache

import android.content.Context
import com.keepcoding.madridshops.repository.db.DBHelper
import com.keepcoding.madridshops.repository.db.build
import com.keepcoding.madridshops.repository.db.dao.ShopDAO
import com.keepcoding.madridshops.repository.model.ShopEntity
import com.keepcoding.madridshops.repository.thread.DispatchOnMainTread
import java.lang.ref.WeakReference

internal class CacheImpl(context: Context): Cache {
    val context = WeakReference<Context>(context)

    override fun getAllShops(entityType: Int, success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            // var shops = ShopDAO(cacheDBHelper()).query()
            var shops = ShopDAO(cacheDBHelper()).query(entityType)
            DispatchOnMainTread(Runnable {
                if (shops.count() > 0) {
                    success(shops)
                } else {
                    error("No shops")
                }
            })
        }).run()
    }

    override fun saveAllShops(entityType: Int, shops: List<ShopEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            try {
                shops.forEach { ShopDAO(cacheDBHelper()).insert(entityType, it) }

                DispatchOnMainTread(Runnable {
                    success()
                })
            } catch(e: Exception) {
                DispatchOnMainTread(Runnable {
                    error("Error inserting shops")
                })
            }
        }).run()
    }

    override fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {
            var successDeleting = ShopDAO(cacheDBHelper()).deleteAll()
            DispatchOnMainTread(Runnable {
                if (successDeleting) {
                    success()
                } else {
                    error("Error deleting")
                }
            })
        }).run()
    }

    private fun cacheDBHelper(): DBHelper {
        return build(context.get() !!, "MadridShops.sqlite", 1)
    }
}