package com.example.dicodingevent.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.response.EventResponse
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listEvents = MutableLiveData<List<ListEventsItem>>() // Menyimpan list events
    val listEvents: LiveData<List<ListEventsItem>> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findEvents() // Memanggil findEvents saat ViewModel diinisialisasi
    }

    // Function to find event data from the API
    fun findEvents(active: Int = 0) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response: Response<EventResponse> = ApiConfig.getApiService().getEvents(active)
                _isLoading.value = false
                if (response.isSuccessful) {
                    // Mengakses listEvents alih-alih events
                    _listEvents.value = response.body()?.listEvents ?: listOf()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message}")
            }
        }
    }

    // Function to post a new review to the API
    fun postReview(eventId: String, review: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response: Response<EventResponse> = ApiConfig.getApiService().postReview(eventId, "Dicoding", review)
                _isLoading.value = false
                if (response.isSuccessful) {
                    // Update UI atau data setelah review diposting
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message}")
            }
        }
    }
}
