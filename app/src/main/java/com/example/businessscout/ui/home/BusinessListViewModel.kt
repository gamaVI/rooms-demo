package com.example.businessscout.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businessscout.MyApplication
import com.example.businessscout.data.AppDatabase
import com.example.businessscout.data.Bussiness
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BusinessListViewModel() : ViewModel() {

    private val _businesses = MutableStateFlow<List<Bussiness>>(emptyList())
    val businesses: StateFlow<List<Bussiness>> = _businesses

    init {
        loadBusinessesForCollection(0)
        viewModelScope.launch {
            val businessesList = MyApplication.database.bussinessDao().getBusinessesInCollection(0)
            _businesses.value = businessesList
        }
    }

    fun loadBusinessesForCollection(collectionId: Int) {
        viewModelScope.launch {
            // Assuming you have a DAO function that fetches businesses by collection ID
            val businessesList = MyApplication.database.bussinessDao().getBusinessesInCollection(collectionId)
            _businesses.value = businessesList
        }
    }



    // Add any other business logic methods needed
}