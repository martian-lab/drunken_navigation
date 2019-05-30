package com.martianlab.drunkennavigation.presentation


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.martianlab.drunkennavigation.DrunkApp

import com.martianlab.drunkennavigation.R
import com.martianlab.drunkennavigation.presentation.viewmodel.ScanViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var qRscanViewModel: ScanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity?.application as DrunkApp).component.inject(this)
        super.onActivityCreated(savedInstanceState)
        qRscanViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(ScanViewModel::class.java)


        if( qRscanViewModel.isLogged() ) {
            findNavController().navigate(R.id.action_loginFragment_to_scanListFragment)
            return
        }


        gobutton.setOnClickListener({
            pin.clearFocus()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
            qRscanViewModel.setPin(pin.text.toString())
        })

        qRscanViewModel.user.observe(viewLifecycleOwner, Observer {
            pin.text.clear()
            if( it != null ) {
                Toast.makeText(context, "Привет, " + it.name, Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_loginFragment_to_scanListFragment)
            }else{
                Toast.makeText(context, "Неизвестный пин", Toast.LENGTH_LONG).show()
            }
        })
    }


}


