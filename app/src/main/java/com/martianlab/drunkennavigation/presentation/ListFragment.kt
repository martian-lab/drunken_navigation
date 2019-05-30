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


        button.setOnClickListener {
            qRscanViewModel.logout()
            findNavController().navigate(R.id.action_scanListFragment_to_loginFragment)
            println("navigate to login")
        }


        adapter = PointRecyclerViewAdapter(listOf())


        qRscanViewModel.items.observe(viewLifecycleOwner, Observer {
                result-> adapter.setValues(result.asReversed())

        })

        qRscanViewModel.getState().observe(viewLifecycleOwner, Observer {
            println("nav dest=" + findNavController()?.currentDestination?.label)
            println("state=" + it )
            Log.d("List","state=" + it )
            if( it == NaviState.WAIT )
                findNavController().navigate(R.id.action_scanListFragment_to_fullListFragment)
        })

        qRscanViewModel._param.value = "000"

        // Set the adapter
        binding.list.adapter = adapter

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */


}
