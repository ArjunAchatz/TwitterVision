package com.example.twittervision

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_twitter_map_visualization.*


class TwitterMapVisualizationActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }

    private val isLocationPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private val locationManager by lazy { getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_map_visualization)
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        if (!isLocationPermissionGranted) {
            requestLocationPermission()
        } else {
            getLocation()
        }
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitude, location.longitude),
                    10f
            ))
            locationManager.removeUpdates(this)
        }
    }

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                1000f,
                this
        )
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    with(AlertDialog.Builder(this)) {
                        title = "No permission granted"
                        setMessage("You'll need to give us permissions to access your location if you want to see the tweets around you.")
                        setPositiveButton("Okay") { dialog: DialogInterface?, _: Int ->
                            dialog?.dismiss()
                            requestLocationPermission()
                        }
                        setNegativeButton("Not now") { dialog: DialogInterface?, _: Int ->
                            dialog?.dismiss()
                        }
                    }.show()
                }
            }
        }
    }
}