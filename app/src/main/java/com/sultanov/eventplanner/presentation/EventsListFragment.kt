package com.sultanov.eventplanner.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sultanov.eventplanner.R

class EventsListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var eventListAdapter: EventListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_events_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(viewLifecycleOwner) {
            eventListAdapter.submitList(it)
            Log.d("EventFragment", it.toString())
        }
    }

    private fun setupRecyclerView(view: View) {
        val rvEventList = view.findViewById<RecyclerView>(R.id.rv_event_list)
        eventListAdapter = EventListAdapter()
        with(rvEventList) {
            adapter = eventListAdapter
            recycledViewPool.setMaxRecycledViews(
                EventListAdapter.VIEW_TYPE_MISS,
                EventListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                EventListAdapter.VIEW_TYPE_AWAIT,
                EventListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                EventListAdapter.VIEW_TYPE_VISIT,
                EventListAdapter.MAX_POOL_SIZE
            )
        }
        eventListAdapter.onEventIconClickListener = {
            viewModel.changeEventState(it)
        }
        setupSwipeListener(rvEventList)
    }

    private fun setupSwipeListener(rvEventList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = eventListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteEventItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvEventList)
    }
}
