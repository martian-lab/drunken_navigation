package com.martianlab.drunkennavigation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.martianlab.drunkennavigation.databinding.FragmentItemListBinding

import kotlinx.android.synthetic.main.fragment_item_list.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ListFragment.OnListFragmentInteractionListener] interface.
 */
class ListFragment : Fragment() {

    lateinit var qRscanViewModel:QRscanViewModel
    lateinit var binding : FragmentItemListBinding

    lateinit var adapter:MyItemRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_item_list,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        qRscanViewModel = ViewModelProviders.of(activity!!).get(QRscanViewModel::class.java)

        adapter = MyItemRecyclerViewAdapter(listOf())


        qRscanViewModel.getItems().observe(viewLifecycleOwner, Observer {
                result-> adapter.setValues(result)

        })


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
