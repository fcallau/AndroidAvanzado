package com.keepcoding.madridshops.repository

import android.content.Context
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.keepcoding.madridshops.repository.cache.Cache
import com.keepcoding.madridshops.repository.cache.CacheImpl
import com.keepcoding.madridshops.repository.model.ShopEntity
import com.keepcoding.madridshops.repository.model.ShopsResponseEntity
import com.keepcoding.madridshops.repository.network.GetJsonManager
import com.keepcoding.madridshops.repository.network.GetJsonManagerVolleyImpl
import com.keepcoding.madridshops.repository.network.json.JsonEntitiesParser
import madridshops.keepcoding.com.repository.BuildConfig
import java.lang.ref.WeakReference

class RepositoryImpl(context: Context): Repository {
    private val weakContext = WeakReference<Context>(context)
    private val cache: Cache = CacheImpl(weakContext.get() !!)

    override fun getAllShops(entityType: Int, success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // read all Shops from cache
        cache.getAllShops(entityType,
            success = {
                // if there's shops in cache --> return them

                success(it)
            }, error = {
                // if no shops in cache --> network

                populateCache(entityType, success, error)
            })
    }

    private fun populateCache(entityType: Int, success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // perform network request

        // FCB
        var serverURL: String

        if (entityType == 1) {
            serverURL = BuildConfig.MADRID_SHOPS_SERVER_URL;
        } else {
            serverURL = BuildConfig.MADRID_ACTIVITIES_SERVER_URL;
        }

        val jsonManager: GetJsonManager = GetJsonManagerVolleyImpl(weakContext.get() !!)
        jsonManager.execute(serverURL, success =  object: SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                val parser = JsonEntitiesParser()
                var responseEntity: ShopsResponseEntity
                try {
                    responseEntity = parser.parse<ShopsResponseEntity>(e)
                } catch (e: InvalidFormatException) {
                    error("ERROR PARSING")
                    return
                }
                // store result in cache
                cache.saveAllShops(entityType, responseEntity.result, success = {
                    success(responseEntity.result)
                }, error = {
                    error("Something happened on the way to heaven!")
                })
            }
        }, error = object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
            }
        })
    }


    override fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit) {

        cache.deleteAllShops(success, error)
    }
}
