package com.martianlab.drunkennavigation.presentation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.martianlab.drunkennavigation.DNaviApp

import com.martianlab.drunkennavigation.R
import com.martianlab.drunkennavigation.presentation.viewmodel.QRscanViewModel
import javax.inject.Inject


class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var qRscanViewModel: QRscanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity?.application as DNaviApp).component.inject(this)
        super.onActivityCreated(savedInstanceState)


    }

}
