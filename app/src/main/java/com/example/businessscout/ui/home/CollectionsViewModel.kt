package com.example.businessscout.ui.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businessscout.MyApplication
import com.example.businessscout.data.BussinessCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CollectionsViewModel : ViewModel() {

    private val _collections = MutableStateFlow<List<BussinessCollection>>(emptyList())
    val collections: StateFlow<List<BussinessCollection>> = _collections
    val count = 0


    init {
        loadCollections()
        // print collections to logcat
        viewModelScope.launch(Dispatchers.IO) {
            val collectionsList = MyApplication.database.businessCollectionDao().getAllCollections()
            collectionsList.forEach { collection ->
                println("Collection: ${collection.collectionName}")
            }
        }
    }



    private fun loadCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            val collectionsList = MyApplication.database.businessCollectionDao().getAllCollections()
            _collections.emit(collectionsList)
        }
    }





   private fun addCollection(collection: BussinessCollection) {
        viewModelScope.launch(Dispatchers.IO) {
            MyApplication.database.businessCollectionDao().insertCollection(collection)
        }
       loadCollections()
    }

    fun deleteCollection(collection: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            MyApplication.database.businessCollectionDao().deleteCollection(collection)
            loadCollections()
        }
    }

    fun insertCollection(collection: BussinessCollection) {
        viewModelScope.launch(Dispatchers.IO) {
            MyApplication.database.businessCollectionDao().insertCollection(collection)
            loadCollections()
        }

    }

    fun updateCollection(collection: BussinessCollection) {
        viewModelScope.launch(Dispatchers.IO) {
            MyApplication.database.businessCollectionDao().updateCollection(collection.id
                , collection.collectionName, collection.colorHex)
            loadCollections()
        }

    }


}