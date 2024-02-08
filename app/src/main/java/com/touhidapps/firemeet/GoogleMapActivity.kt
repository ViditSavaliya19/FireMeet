package com.touhidapps.firemeet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.touhidapps.firemeet.databinding.ActivityGoogleMapBinding

class GoogleMapActivity : AppCompatActivity(),OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_map)

        val supportFragment = supportFragmentManager.findFragmentById(R.id.mapContainer) as? SupportMapFragment
        supportFragment!!.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {


        googleMap.uiSettings.isZoomControlsEnabled =true
        googleMap.uiSettings.isMyLocationButtonEnabled =true
        googleMap.isMyLocationEnabled = true

//        googleMap.setMinZoomPreference(0.5f)
//        googleMap.setMaxZoomPreference(0.5f)

        var surat = LatLng(21.1702,72.8311)
        var vesu = LatLng(21.1418, 72.7709)
        var adajan = LatLng(21.1959,72.7933)
//        googleMap.addMarker(MarkerOptions().position(surat).title("Diamond city"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(surat))

//        var mumbai = LatLng(19.0760,72.8777)
//        googleMap.addMarker(MarkerOptions().position(mumbai).title("Film city"))
//        googleMap.addCircle(CircleOptions().center(mumbai).radius(20000.0))
        googleMap.addPolygon(PolygonOptions().add(surat).add(vesu).add(adajan))

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(surat))
        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 10.0f ) );

    }
}