package com.keepcoding.madridshops.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.adapter.ItemRecyclerViewAdapter
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.interactor.getallshops.GetAllShopsInteractor
import com.keepcoding.madridshops.domain.interactor.getallshops.GetAllShopsInteractorImpl
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops
import com.keepcoding.madridshops.router.Router
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MapListActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {
    var shops: Shops? = null

    companion object {
        val EXTRA_TEXT_BUTTON = "EXTRA_TEXT_BUTTON"

        fun intent(context: Context, textButton: String) : Intent {
            val intent = Intent(context, MapListActivity::class.java)

            intent.putExtra(EXTRA_TEXT_BUTTON, textButton)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        setContentView(R.layout.content_main)
        // setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        Log.d("App", "onCreate MapListActivity")
        Log.d("TEXT_BUTTON", "> " + intent.getStringExtra(EXTRA_TEXT_BUTTON))

        setupMap()
    }

    private fun setupList(shops: Shops) {
        val listFragment = supportFragmentManager.findFragmentById(R.id.activity_main_list_fragment)
        val mainActivityContext = this.baseContext

        val adapter = ItemRecyclerViewAdapter(shops.all())
        listFragment.items_list.layoutManager = GridLayoutManager(this, 1)
        listFragment.items_list.itemAnimator = DefaultItemAnimator()
        listFragment.items_list.adapter = adapter



        adapter.onClickListener = View.OnClickListener { v: View ->
            val position = listFragment.items_list.getChildAdapterPosition(v)
            val shop = shops.get(position)

            // Router().navigateFromMainActivityToDetailActivity(this, shop)
            Router().navigateFromMapListActivityToDetailActivity(this, shop)
        }
    }

    private fun setupMap() {
        val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImpl(this)
        getAllShopsInteractor.execute(object: SuccessCompletion<Shops> {
            override fun successCompletion(shops: Shops) {
                this@MapListActivity.shops = shops

                initializeMap(shops)

                setupList(shops)
            }

        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Toast.makeText(baseContext, "Error ", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initializeMap(shops: Shops) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.activity_main_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync({ mapa ->
            Log.d("SUCCESS", "HABEMUS MAPA")

            centerMapInPosition(mapa,40.416775,-3.703790)
            mapa.uiSettings.isRotateGesturesEnabled = false
            mapa.uiSettings.isZoomControlsEnabled = true
            showUserPosition(baseContext, mapa)
            map = mapa
            addAllPins(shops)
        })
    }

    fun centerMapInPosition(map: GoogleMap, latitude: Double, longitude: Double) {
        val coordinate = LatLng(latitude, longitude)
        val cameraPosition = CameraPosition.Builder().
                target(coordinate).
                zoom(15f).
                build()

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun showUserPosition(context: Context, map: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 10)

            return
        }
    }

    private var map: GoogleMap? = null

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {
            try {
                map?.isMyLocationEnabled = true
            } catch (e: SecurityException) {

            }
        }
    }

    fun addAllPins(shops: Shops) {
        for (i in 0 until shops.count()) {
            val shop = shops.get(i)
            addPin(this.map !!, shop.latitude.toDouble(), shop.longitude.toDouble(), shop.name, i)
        }

        this.map?.setOnMarkerClickListener(this)

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        val position = p0?.tag as Int

        // Router().navigateFromMainActivityToDetailActivity(this, shopInPosition(position))

        return false
    }

    fun shopInPosition(position: Int): Shop {
        return this.shops?.get(position)!!
    }

    fun addPin(map: GoogleMap, latitude: Double, longitude: Double, title: String, position: Int) {
        val marker = map.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title(title))

        marker.tag = position
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item?.itemId == android.R.id.home) {
            this.finish()
        } else {
            Router().navigateFromMapListActivityToPicassoActivity(this)
        }

        return true
    }
}
