package com.example.businessscout.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.businessscout.databinding.FragmentBusinessBinding
import kotlinx.coroutines.launch
import com.example.businessscout.data.Bussiness

class BusinessListFragment : Fragment() {

    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BusinessListViewModel
    private lateinit var businessAdapter: BusinessAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        binding.businessListRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this).get(BusinessListViewModel::class.java)
        businessAdapter = BusinessAdapter()
        binding.businessListRecyclerView.adapter = businessAdapter

       lifecycleScope.launch {
            viewModel.businesses.collect { businesses ->
                updateRecyclerView(businesses)
                // log the businesses to logcat
                businesses.forEach { business ->
                    println("Business from fragment: ${business.naziv}")
                }
            }
        }

        return binding.root
    }

    private fun updateRecyclerView(businesses: List<Bussiness>) {
        businessAdapter.setBusinesses(businesses)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}