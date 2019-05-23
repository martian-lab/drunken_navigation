package com.martianlab.drunkennavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    lateinit var qRscanViewModel:QRscanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        qRscanViewModel  = ViewModelProviders.of(this).get(QRscanViewModel::class.java)
    }
}
