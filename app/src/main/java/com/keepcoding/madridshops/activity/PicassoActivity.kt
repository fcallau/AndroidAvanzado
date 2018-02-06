package com.keepcoding.madridshops.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.keepcoding.madridshops.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_picasso.*

class PicassoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picasso)

        Picasso.with(this).setIndicatorsEnabled(true)
        Picasso.with(this).isLoggingEnabled = true


        Picasso.
                with(this).
                load("http://stillcracking.com/wp-content/uploads/2014/05/c5731d46405fb1226cad6eef30c99ce145e7894eb7948b4180e78e0b3a32aaca_1.jpg").
                placeholder(android.R.drawable.ic_delete).
                into(img1)


        Picasso.
                with(this).
                load("http://i0.kym-cdn.com/photos/images/newsfeed/000/716/080/f27.jpg").
                placeholder(android.R.drawable.ic_btn_speak_now).
                into(img2)


        Picasso.
                with(this).
                load("https://i.redd.it/b8n95p73ild01.jpg").
                placeholder(android.R.drawable.ic_input_add).
                into(img3)

    }
}
