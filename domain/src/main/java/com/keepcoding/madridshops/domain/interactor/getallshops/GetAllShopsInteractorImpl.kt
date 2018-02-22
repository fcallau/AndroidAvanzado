package com.keepcoding.madridshops.domain.interactor.getallshops

import android.content.Context
import android.util.Log
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops
import com.keepcoding.madridshops.repository.Repository
import com.keepcoding.madridshops.repository.RepositoryImpl
import com.keepcoding.madridshops.repository.model.ShopEntity
import java.lang.ref.WeakReference
import java.util.*

class GetAllShopsInteractorImpl(context: Context) : GetAllShopsInteractor {
    private val weakContext = WeakReference<Context>(context)
    private val repository: Repository = RepositoryImpl(weakContext.get() !!)

    override fun execute(entityType: Int, success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        repository.getAllShops(entityType, success = {
            val shops: Shops = entityMapper(it)
            success.successCompletion(shops)
        }, error = {
            error(it)
        })
    }

    private fun entityMapper(list: List<ShopEntity>): Shops {
        val tempList = ArrayList<Shop>()
        list.forEach {
            var latitude: Float = 0.0f
            var longitude: Float = 0.0f


            Log.d("Before format", "latitude: " + it.latitude + ", longitude: " + it.longitude)

            try {
                latitude = it.latitude.toFloat()
                longitude = it.longitude.toFloat()

                val shop = Shop(it.id.toInt(), it.name, it.address, it.description, latitude, longitude, it.img, it.logo, it.openingHours)

                tempList.add(shop)
            } catch(e: Exception) {
                Log.d("Bad Long format", "e: " + e.toString() + ". latitude: " + it.latitude + ", longitude: " + it.longitude)
            }

            // Log.d("Lat&long used", "latitude: " + it.latitude + ", longitude: " + it.longitude)

            /*val shop = Shop(it.id.toInt(), it.name, it.address, it.description, it.latitude, it.longitude, it.img, it.logo,
                    it.openingHours)*/

            /*val shop = Shop(it.id.toInt(), it.name, it.address, it.description, latitude, longitude, it.img, it.logo,
                    it.openingHours)*/

            // tempList.add(shop)
        }

        val shops = Shops(tempList)
        return shops

    }
}


