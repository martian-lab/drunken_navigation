package com.martianlab.drunkennavigation.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.martianlab.drunkennavigation.DrunkApp
import com.martianlab.drunkennavigation.R
import com.martianlab.drunkennavigation.databinding.FragmentListBinding
import com.martianlab.drunkennavigation.domain.NaviState
import com.martianlab.drunkennavigation.presentation.adapters.PointRecyclerViewAdapter
import com.martianlab.drunkennavigation.presentation.viewmodel.ScanViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ListFragment.OnListFragmentInteractionListener] interface.
 */
class ListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var qRscanViewModel: ScanViewModel

    lateinit var binding : FragmentListBinding

    lateinit var adapter: PointRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity?.application as DrunkApp).component.inject(this)
        super.onActivityCreated(savedInstanceState)
        //qRscanViewModel = ViewModelProviders.of(activity!!).get(QRscanViewModel::class.java)
        qRscanViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(ScanViewModel::class.java)


        println("nav dest=" + findNavController()?.currentDestination?.label)

        button.setOnClickListener {
            qRscanViewModel.logout()

            if( findNavController()?.currentDestination?.label?.equals("fragment_scan_list")?:false  )
                findNavController().navigate(R.id.action_scanListFragment_to_loginFragment)
            else if( findNavController()?.currentDestination?.label?.equals("fragment_list")?:false  )
                findNavController().navigate(R.id.action_listFragment_to_loginFragment)

        }


        adapter = PointRecyclerViewAdapter(listOf())


        qRscanViewModel.items.observe(viewLifecycleOwner, Observer {
                result-> adapter.setValues(result.sortedBy { it.time })
        })

        qRscanViewModel.retry()

        qRscanViewModel.getState().observe(viewLifecycleOwner, Observer {

            if( it == NaviState.WAIT && adapter.itemCount > 0 )
                if( findNavController()?.currentDestination?.label?.equals("fragment_scan_list")?:false  )
                    findNavController().navigate(R.id.action_scanListFragment_to_listFragment)

        })


        // Set the adapter
        binding.list.adapter = adapter

    }



}
