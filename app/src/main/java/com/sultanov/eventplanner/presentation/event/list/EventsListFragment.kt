package com.sultanov.eventplanner.presentation.event.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sultanov.eventplanner.databinding.FragmentEventsListBinding
import com.sultanov.eventplanner.presentation.Mode
import kotlinx.coroutines.launch

internal class EventsListFragment : Fragment() {

    private var _binding: FragmentEventsListBinding? = null
    private val binding: FragmentEventsListBinding
        get() = _binding ?: throw RuntimeException("FragmentEventsListBinding == null")

    private lateinit var eventListAdapter: EventListAdapter

    private val viewModel by viewModels<EventsViewModel> { EventsViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.addEventItem.setOnClickListener {
            findNavController().navigate(
                EventsListFragmentDirections.actionEventsListFragmentToEventItemFragment(Mode.Add)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        eventListAdapter = EventListAdapter()
        with(binding.rvEventList) {
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventsFlow.collect { events ->
                eventListAdapter.submitList(events)
            }
        }
        eventListAdapter.onEventIconClickListener = {
            viewModel.viewModelScope.launch {
                viewModel.changeEventState(it)
            }
        }
        eventListAdapter.onEventItemClickListener = {
            findNavController().navigate(
                EventsListFragmentDirections
                    .actionEventsListFragmentToEventItemFragment(Mode.Edit(it.id))
            )
        }
        setupSwipeListener(binding.rvEventList)
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

                lifecycleScope.launch {
                    viewModel.deleteEvent(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvEventList)
    }
}
