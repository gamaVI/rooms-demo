package com.example.businessscout.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businessscout.MyApplication
import com.example.businessscout.data.Bussiness
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _businesses = MutableLiveData<List<Bussiness>>()
    val businesses: LiveData<List<Bussiness>> = _businesses

    init {
        loadBusinesses()
    }

    private fun loadBusinesses() {
        viewModelScope.launch(Dispatchers.IO) {
            val businessList = MyApplication.database.bussinessDao().getAllBusinesses()
            _businesses.postValue(businessList)
        }
    }
}