package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.myapplication.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        locationManager()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        mMap.isMyLocationEnabled = true

    }
    var oriLocation : Location? = null

    fun locationManager(){
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        var isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        var isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!(isGPSEnabled || isNetworkEnabled))
            Toast.makeText(this, "目前無開啟任何定位功能",Toast.LENGTH_LONG).show()
        else
            try {
                if (isGPSEnabled ) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0L, 0f, locationListener)
                    oriLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
                else if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        0L, 0f, locationListener)
                    oriLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
            } catch(ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available")
            }
        if(oriLocation != null) {
            drawMarker()
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(oriLocation!!.latitude, oriLocation!!.longitude), 17.0f))
        }
    }
    val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if(oriLocation == null) {
                oriLocation = location
                drawMarker()
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17.0f))

        }
    }
    fun drawMarker(){
        var lntLng = LatLng(oriLocation!!.latitude, oriLocation!!.longitude)
        mMap.addMarker(MarkerOptions().position(lntLng).title("Current Position"))
    }


}