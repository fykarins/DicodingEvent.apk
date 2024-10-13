package com.example.dicodingevent.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.response.EventResponse
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents

    init {
        fetchUpcomingEvents()
    }

    private fun fetchUpcomingEvents() {
        // Menggunakan coroutine untuk memindahkan operasi network ke background thread
        viewModelScope.launch {
            try {
                // Panggil suspending function dari ApiService
                val response: Response<EventResponse> = ApiConfig.getApiService().getEvents(active = 0)
                if (response.isSuccessful) {
                    // Mengakses listEvents alih-alih events
                    _upcomingEvents.value = response.body()?.listEvents ?: listOf()
                } else {
                    _upcomingEvents.value = listOf() // Jika response gagal, beri data kosong
                }
            } catch (e: Exception) {
                _upcomingEvents.value = listOf() // Pada kegagalan, beri data kosong
            }
        }
    }
}
