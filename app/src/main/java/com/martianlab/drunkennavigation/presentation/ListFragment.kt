package com.martianlab.drunkennavigation.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.martianlab.drunkennavigation.DrunkApp
import com.martianlab.drunkennavigation.R
import com.martianlab.drunkennavigation.databinding.FragmentListBinding
import com.martianlab.drunkennavigation.presentation.adapters.PointRecyclerViewAdapter
import com.martianlab.drunkennavigation.presentation.viewmodel.ScanViewModel
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


        adapter = PointRecyclerViewAdapter(listOf())


        qRscanViewModel.items.observe(viewLifecycleOwner, Observer {
                result-> adapter.setValues(result)

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
