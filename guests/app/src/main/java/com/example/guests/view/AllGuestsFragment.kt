package com.example.guests.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guests.R
import com.example.guests.view.adapter.GuestAdapter
import com.example.guests.viewmodel.AllGuestsViewModel
import kotlinx.android.synthetic.main.fragment_all.*

class AllGuestsFragment : Fragment() {

    private lateinit var allGuestsViewModel: AllGuestsViewModel
    private val mAdapter: GuestAdapter = GuestAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        allGuestsViewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.all_guests_view)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        observer()

        allGuestsViewModel.load()

        return root
    }

    private fun observer() {
        //viewLifecycleOwner has the same role for fragments as Context has for Activities
        allGuestsViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })
    }
}