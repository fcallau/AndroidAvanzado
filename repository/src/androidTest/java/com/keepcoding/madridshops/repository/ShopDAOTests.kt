package com.keepcoding.madridshops.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.keepcoding.madridshops.repository.db.build
import com.keepcoding.madridshops.repository.db.dao.ShopDAO
import com.keepcoding.madridshops.repository.model.ShopEntity
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ShopDAOTests {
    val appContext = InstrumentationRegistry.getTargetContext()
    val dbhelper = build(appContext, "mydb.sqlite", 1)

    @Test
    @Throws(Exception::class)
    fun given_valid_shopentity_it_gets_inserted_correctly() {

        val shop = ShopEntity(1,1,"My shop", ""
                , 1.0f, 2.0f,"","", "", "")

        val shopEntityDao = ShopDAO(dbhelper)

        val id = shopEntityDao.insert(shop)

        assertTrue(id > 0)
    }

    // TODO: convert into a valid test
    private fun test() {
        val dbhelper = build(appContext, "mydb.sqlite", 1)

        val shopEntityDao = ShopDAO(dbhelper)


        // val deletedAll = shopEntityDao.deleteAll()

        val shop = ShopEntity(1,1,"My shop 1", "desc 1"
                , 1.0f, 2.0f,"","", "", "")


        val shop2 = ShopEntity(2,1,"My shop 2", "desc 2"
                , 1.0f, 2.0f,"","", "", "")


        val id = shopEntityDao.insert(shop)
        val id2 = shopEntityDao.insert(shop2)

        shopEntityDao.query().forEach {
            Log.d("Shop", it.name + " - " + it.description)

        }

    }
}
