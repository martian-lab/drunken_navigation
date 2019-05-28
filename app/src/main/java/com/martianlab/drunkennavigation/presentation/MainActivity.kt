package com.martianlab.drunkennavigation.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.martianlab.drunkennavigation.R
import com.martianlab.drunkennavigation.di.DaggerActivity
import com.martianlab.drunkennavigation.presentation.viewmodel.QRscanViewModel
import javax.inject.Inject

class MainActivity : DaggerActivity() {

    //lateinit var qRscanViewModel : ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //qRscanViewModel  = ViewModelProviders.of(this).get(QRscanViewModel::class.java)
    }
}
