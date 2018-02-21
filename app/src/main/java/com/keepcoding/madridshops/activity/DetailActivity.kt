package com.keepcoding.madridshops.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Shop
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    companion object {
        val EXTRA_SHOP = "EXTRA_SHOP"

        fun intent(context: Context, aValue: String, aShop: Shop) : Intent {
            val intent = Intent(context, DetailActivity::class.java)

            intent.putExtra(EXTRA_SHOP, aShop)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        var description = this.findViewById<TextView>(R.id.detail_description)
        var image = this.findViewById<ImageView>(R.id.detail_image)
        var address = this.findViewById<TextView>(R.id.detail_address)
        var map = this.findViewById<ImageView>(R.id.detail_map)

        val myShop: Shop = intent.getSerializableExtra(EXTRA_SHOP) as Shop

        this.title = myShop.name

        description.text = myShop.description

        Picasso.with(this).
                load(myShop.img).
                placeholder(android.R.drawable.ic_delete).
                into(image)

        address.text = myShop.address

        Picasso.with(this).
                load("https://maps.googleapis.com/maps/api/staticmap?%25&size=320x220&scale=2&markers=" + myShop.latitude + "," + myShop.longitude).
                placeholder(android.R.drawable.ic_delete).
                into(map)
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.d("onOptionsItemSelected", "item: " + item?.itemId)
        Log.d("onOptionsItemSelected", "android.R.id.home: " + android.R.id.home)

        if (item?.itemId == android.R.id.home) {
            this.finish()
        }

        return true
    }
}