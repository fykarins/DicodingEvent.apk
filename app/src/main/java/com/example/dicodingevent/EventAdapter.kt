package com.example.dicodingevent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.databinding.ItemEventBinding

class EventAdapter(private var listEvents: List<ListEventsItem> = listOf()) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    // ViewHolder untuk RecyclerView
    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvEventName.text = event.name // Pastikan tvEventName ada di item_event.xml
            binding.tvEventDescription.text = event.description // Pastikan tvEventDescription ada di item_event.xml
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(listEvents[position])
    }

    override fun getItemCount(): Int = listEvents.size

    // Method untuk memperbarui data di RecyclerView
    fun updateData(newEvents: List<ListEventsItem>) {
        listEvents = newEvents
        notifyDataSetChanged()
    }
}
