package com.martianlab.drunkennavigation.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.martianlab.drunkennavigation.R
import com.martianlab.drunkennavigation.di.DaggerActivity
import java.io.IOException

class MainActivity : DaggerActivity() {

    //lateinit var qRscanViewModel : ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            /* Asking user to allow access of camera */
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                setContentView(R.layout.activity_main)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1024);
            }
        } catch (e: IOException) {
            Log.e("Camera start error-->> ", e.message.toString())
        }

        //setContentView(R.layout.activity_main)


        //qRscanViewModel  = ViewModelProviders.of(this).get(QRscanViewModel::class.java)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
             1024 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    setContentView(R.layout.activity_main)
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "You NEED this permission!", Toast.LENGTH_LONG).show()
                    finish()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
