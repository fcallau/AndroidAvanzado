package com.keepcoding.madridshops

import android.support.multidex.MultiDexApplication
import android.util.Log
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.interactor.getallshops.GetAllShopsInteractorImpl
import com.keepcoding.madridshops.domain.model.Shops


/*

- UI(App) --> Interactor --> Repository --> Cache --> Network / DB

Borrado cache:
- UI(App) --> Interactor --> Repository --> Cache.delete
Model <----- Model <-> Entity <------ Entity <---- Entity

GetAllShops:
- UI(App) --> Interactor(GetAllShopsInteractor) --> Repository(getAllShops) --> Cache.getAllShops --> DAO Cache.isValid
Repository(getAllShops) --> Network.getAllShops
Repository(getAllShops) --> Cache.cacheAllShops --> DAO

 */

class MadridShopsApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // init code application wide

        Log.d("App", "onCreate")

        Log.d("App", BuildConfig.MADRID_SHOPS_SERVER_URL)



        val allShopsInteractor = GetAllShopsInteractorImpl(this)

        allShopsInteractor.execute(object: SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {
                Log.d("Shops", "Count: " + shops.count())

                shops.shops.forEach { Log.d("Shop", it.name) }
            }
        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Log.d("ERROR", errorMessage)
            }
        })


        /*

        DeleteAllShopsImpl(this).execute(success = {
            Log.d("success", "success")
        }, error = {
            Log.d("error", "error deleting " + it)

        })
    */
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}