package com.example.mdlesson.ui.settingUi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mdlesson.R
import com.example.mdlesson.databinding.FragmentImageBinding
import com.example.mdlesson.databinding.FragmentSettingBinding
import com.example.mdlesson.ui.settingUi.recycylerview.RecyclerViewSampleViewModel
import com.example.mdlesson.ui.settingUi.recycylerview.adapter.ItemDragTouchHelperCallback
import com.example.mdlesson.ui.settingUi.recycylerview.adapter.SampleAdapter
import com.example.mdlesson.ui.settingUi.recycylerview.adapter.SampleDiffUtil

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RecyclerViewSampleViewModel>()

    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy {
        SampleAdapter(
            onPlanetClickListener = { planet -> viewModel.onPlanetClick(planet) },
            onAdvertisingClickListener = { advertising -> viewModel.onAdvertisingClick(advertising) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvPlanetsList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val callback = ItemDragTouchHelperCallback(
            onItemMove = { from, to ->
                viewModel.onItemMoved(from, to)
            },
            onItemSwiped = { position -> viewModel.onItemRemoved(position) }
        )
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        observeViewModel()

        viewModel.loadData()
    }

    private fun observeViewModel() {
        viewModel.getItems().observe(viewLifecycleOwner) { items ->
//            adapter.items = items
//            adapter.notifyDataSetChanged()

            val sampleDiffUtil = SampleDiffUtil(
                oldList = adapter.items,
                newList = items
            )
            val sampleDiffResult = DiffUtil.calculateDiff(sampleDiffUtil)
            adapter.items = items
            sampleDiffResult.dispatchUpdatesTo(adapter)
        }

        viewModel.getMessage().observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}