package com.keepcoding.madridshops.domain.model

import java.util.*

/**
 * Shop: represents one Shop
 *
 */
data class Shop(val id: Int, val name: String, val address: String, val description: String,
                val latitude: Float, val longitude: Float, val img: String, val logo: String, val openingHours: String) {
    init {
       Shops(ArrayList<Shop>())
    }
}

/*val id: Long,
val databaseId: Long,
val name: String,
@JsonProperty("description_en") val description: String,
@JsonProperty("gps_lat") val latitude: String,
@JsonProperty("gps_lon") val longitude: String,
val img: String,
@JsonProperty("logo_img") val logo: String,
@JsonProperty("opening_hours_en")val openingHours: String,
val address: String*/

class Shops(val shops: MutableList<Shop>): Aggregate<Shop> {
    override fun count(): Int = shops.size

    override fun all(): List<Shop> = shops

    override fun get(position: Int): Shop {
        return shops.get(position)
    }

    override fun add(element: Shop) {
        shops.add(element)
    }

    override fun delete(position: Int) {
        shops.removeAt(position)
    }

    override fun delete(element: Shop) {
        shops.remove(element)
    }
}