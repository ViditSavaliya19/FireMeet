package com.touhidapps.firemeet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.touhidapps.firemeet.databinding.ActivityGoogleMapBinding

class GoogleMapActivity : AppCompatActivity(),OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_map)

        val supportFragment = supportFragmentManager.findFragmentById(R.id.mapContainer) as? SupportMapFragment
        supportFragment!!.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        var surat = LatLng(21.1702,72.8311)
        googleMap.addMarker(MarkerOptions().position(surat).title("Diamond city"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(surat))
    }
}