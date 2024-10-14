package com.example.dicodingevent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.DetailEventResponse
import com.example.dicodingevent.databinding.ActivityDetailBinding
import com.example.dicodingevent.ui.upcoming.UpcomingViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var eventAdapter: EventAdapter
    private lateinit var upcomingViewModel: UpcomingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar untuk tampilan header
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Dicoding Event"
            setDisplayHomeAsUpEnabled(true) // Aktifkan tombol back
            setHomeAsUpIndicator(R.drawable.ic_arrow_back) // Custom icon back (jika perlu)
        }

        // Event ketika tombol back ditekan
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // ViewModel dan observer untuk detail event
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val eventId = intent.getStringExtra("EVENT_ID") ?: return
        detailViewModel.fetchEventDetail(eventId)
        observeEventDetail()

        // Set listener untuk tombol register
        binding.btnRegister.setOnClickListener {
            val registrationUrl = detailViewModel.eventDetail.value?.event?.link ?: return@setOnClickListener
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(registrationUrl)
            startActivity(intent)
        }

        // Setup ViewModel untuk upcoming events
        upcomingViewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)

        // Setup RecyclerView
        binding.rvUpcomingEvents.layoutManager = LinearLayoutManager(this)
        eventAdapter = EventAdapter() // Inisialisasi adapter
        binding.rvUpcomingEvents.adapter = eventAdapter

        // Observe upcoming events
        observeUpcomingEvents()
    }

    private fun observeEventDetail() {
        detailViewModel.eventDetail.observe(this, Observer { detailResponse: DetailEventResponse? ->
            detailResponse?.let {
                val event = it.event

                // Menampilkan informasi event
                binding.tvEventName.text = event.name // Nama Event
                binding.tvDescription.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY) // Deskripsi Event

                // Menampilkan gambar
                Glide.with(this)
                    .load(event.mediaCover) // Gambar dari mediaCover
                    .into(binding.imageView) // Ganti dengan id ImageView yang sesuai di layout Anda

                // Menampilkan informasi tambahan (jika diperlukan)
                binding.tvEventOwner.text = event.ownerName // Nama Pemilik
                binding.tvEventCity.text = event.cityName // Nama Kota
                binding.tvQuota.text = "Quota: ${event.quota}" // Kuota
                binding.tvRegistrants.text = "Registrants: ${event.registrants}" // Registrants
                binding.tvEventTime.text = "${event.beginTime} - ${event.endTime}" // Waktu Event
            } ?: run {
                Log.e("DetailActivity", "Event detail is null")
            }
        })
    }

    private fun observeUpcomingEvents() {
        upcomingViewModel.upcomingEvents.observe(this, Observer { events ->
            if (events != null && events.isNotEmpty()) {
                eventAdapter.submitList(events) // Mengirimkan daftar event ke adapter
            } else {
                Toast.makeText(this, "No upcoming events available", Toast.LENGTH_SHORT).show()
            }
        })
    }
}