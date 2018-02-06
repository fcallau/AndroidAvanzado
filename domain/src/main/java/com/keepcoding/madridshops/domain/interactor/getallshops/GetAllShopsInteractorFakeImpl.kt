package com.keepcoding.madridshops.domain.interactor.getallshops

import com.keepcoding.madridshops.domain.interactor.ErrorClosure
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessClosure
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops
import java.util.*

class GetAllShopsInteractorFakeImpl: GetAllShopsInteractor {
    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        var allOk = true

        // connect to the repository

        if (allOk) {
            val shops = createFakeListOfShops()

            success.successCompletion(shops)
        } else {
            error.errorCompletion("Error while accessing the Repository")
        }
    }

    fun execute(success: SuccessClosure, error: ErrorClosure) {
        var allOk = true

        // connect to the repository

        if (allOk) {
            val shops = createFakeListOfShops()

            success(shops)
        } else {
            error("Error while accessing the Repository")
        }
    }

    fun createFakeListOfShops(): Shops {
        val list = ArrayList<Shop>()

        for (i in 0..100) {
            val shop = Shop(i, address = "Shop " + i, name = "Address " + i)
            list.add(shop)
        }

        val shops = Shops(list)
        return shops
    }
}