package com.example.guests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guests.service.model.GuestModel
import com.example.guests.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int){
        when(filter){
            0 -> mGuestList.value = mGuestRepository.getAll()
            1 -> mGuestList.value = mGuestRepository.getPresent()
            2 -> mGuestList.value = mGuestRepository.getAbsent()
        }

    }

    fun delete(id: Int) {
        mGuestRepository.delete(id)
    }
}