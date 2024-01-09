package com.example.businessscout.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.businessscout.MyApplication
import com.example.businessscout.R
import com.example.businessscout.data.BussinessCollection
import com.example.businessscout.databinding.FragmentCollectionsBinding
import kotlinx.coroutines.launch

class CollectionsFragment : Fragment() {
    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var collectionsAdapter: CollectionsAdapter
    private lateinit var collectionsViewModel: CollectionsViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        binding.collectionsRecyclerView.layoutManager = LinearLayoutManager(context)

        collectionsViewModel = ViewModelProvider(this).get(CollectionsViewModel::class.java)
        collectionsAdapter = CollectionsAdapter(collectionsViewModel,object : CollectionsAdapter.OnCollectionClickListener {
            override fun onCollectionClick(collectionId: Int) {
                Log.println(Log.INFO, "CollectionsFragment", "Collection ID: $collectionId")
                val businessListFragment = BusinessListFragment().apply {
                    arguments = Bundle().apply {
                        putInt("collectionId", collectionId)
                    }
                }
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, businessListFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        )

        binding.collectionsRecyclerView.adapter = collectionsAdapter

        lifecycleScope.launch {
            collectionsViewModel.collections.collect { collections ->
                updateRecyclerView(collections)
                // log the collections to logcat
                collections.forEach { collection ->
                    println("Collection from fragment: ${collection.collectionName}")
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        lifecycleScope.launch {
            collectionsViewModel.collections.collect { collections ->
                updateRecyclerView(collections)
                // log the collections to logcat
                collections.forEach { collection ->
                    println("Collection from fragment: ${collection.collectionName}")
                }
            }
        }
        super.onResume()
    }

     fun onCollectionClick(collectionId: Int) {
        val businessListFragment = BusinessListFragment().apply {
            arguments = Bundle().apply {
                putInt("collectionId", collectionId)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, businessListFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateRecyclerView(collections: List<BussinessCollection>) {
        collectionsAdapter.setCollections(collections)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


